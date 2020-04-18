package arrow.di

import arrow.typeclasses.DaoDatabase
import io.reactivex.rxjava3.disposables.Disposable

class MyActivity {

    private var disposable = Disposable.disposed()

    fun onStart() {
        disposable = dependenciesAsTypes.run { 1.fetchUser() }.subscribe()
    }

    fun onStop() {
        disposable.dispose()
    }
}

val dependenciesAsTypes: RequestOperations = object : RequestOperations {
    override val dao: DaoDatabase = DaoDatabase()
    override val network: NetworkModule = NetworkModule()
}