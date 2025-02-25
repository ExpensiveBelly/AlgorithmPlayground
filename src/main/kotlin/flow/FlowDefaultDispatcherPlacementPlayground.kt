package flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking

// Dummy classes and functions to make the code compile
private sealed class StreamStatus {
  data class SubscribeToChartUpdates(val requestId: String) : StreamStatus()
}

private data class Bar(val price: Double, val volume: Double)

private interface TradingViewRepository {
  fun streamChartUpdates(productCode: String): Flow<Pair<Double, Double>>
}

private val lastBarFlow = MutableStateFlow<Bar?>(null)
private val productCodeFlow = MutableStateFlow("BTCUSD")
private val streamStatusFlow = MutableStateFlow<StreamStatus?>(null)

private fun processBarsStream(price: Double, volume: Double): Pair<Bar?, String?> {
  Thread.sleep(100) // Simulate work
  return Pair(Bar(price, volume), "some script")
}

private val tradingViewRepository =
    object : TradingViewRepository {
      override fun streamChartUpdates(productCode: String): Flow<Pair<Double, Double>> = flow {
        emit(Pair(100.0, 10.0))
        delay(50)
        emit(Pair(101.0, 12.0))
      }
    }

private fun <T> Flow<T>.stateInWhileSubscribed(
    scope: CoroutineScope,
    initialValue: T,
    started: SharingStarted = SharingStarted.WhileSubscribed(5000)
) = stateIn(scope, started, initialValue)

private fun <T> Flow<T>.shareInWhileSubscribed(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5000),
    replay: Int = 0
) = shareIn(scope, started, replay)

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
  val defaultDispatcher = Dispatchers.Default
  // Single-threaded for demonstration
  val viewModelScope = this // Simulate viewModelScope

  val streamChartUpdateFlow: SharedFlow<String?> =
      combine(lastBarFlow, productCodeFlow, streamStatusFlow, ::Triple)
          .filter { (lastBar, _, request) ->
            println("1: ${Thread.currentThread().name}")
            lastBar != null && request is StreamStatus.SubscribeToChartUpdates
          }
          .flatMapLatest { (lastBar, productCode, request) ->
            tradingViewRepository.streamChartUpdates(productCode = productCode).mapLatest {
                (price, volume) ->
              println("2: ${Thread.currentThread().name}")
              val barData =
                  processBarsStream(
                      price = price,
                      volume = volume,
                  )
              val newBar = barData.first
              val script = barData.second
              lastBarFlow.update { newBar }
              script
            }
          }.flowOn(defaultDispatcher)
          .shareInWhileSubscribed(viewModelScope + defaultDispatcher)
//          .shareInWhileSubscribed(viewModelScope + defaultDispatcher) //This has the same effect as the `flowOn` above

  lastBarFlow.value = Bar(99.0, 9.0)
  streamStatusFlow.value = StreamStatus.SubscribeToChartUpdates("req1")
  streamChartUpdateFlow
      .map {
        println("3: ${Thread.currentThread().name}")
        it
      }
      .collect { println("4: ${Thread.currentThread().name}") }
  println("--- Completed ---\n")

  println("Shutting down...")
  this.cancel()
}
