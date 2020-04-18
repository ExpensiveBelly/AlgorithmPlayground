package arrow.di

import arrow.typeclasses.Index
import arrow.typeclasses.realWorld
import io.reactivex.rxjava3.core.Single

typealias Request = String

data class UserDto(val id: Index)

class NetworkModule {

    fun fetch(id: Int, headers: Map<String, String>): Single<UserDto> = realWorld {
        Single.fromCallable { UserDto(id) }.toObservable().replay(1).refCount().firstOrError()
    }
}
