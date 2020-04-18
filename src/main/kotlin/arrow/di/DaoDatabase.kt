@file:Suppress("unused")

package arrow.typeclasses

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

typealias Query = String

data class UserDao(val id: Int)

class DaoDatabase {
    fun query(s: Query): @NonNull Single<UserDao> = realWorld {
        Single.fromCallable { UserDao(1) }.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
    }
}
