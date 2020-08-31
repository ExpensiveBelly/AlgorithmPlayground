package arrow.di

import arrow.typeclasses.Index
import arrow.typeclasses.User
import io.reactivex.rxjava3.core.Single

interface RequestOperations : DaoOperations, NetworkOperations, DomainMapper {
    fun Index.fetchUser(): Single<User> =
        queryUser().toUserFromDatabase()
            .onErrorResumeNext { requestUser().toUserFromNetwork() }
            .toObservable().replay(1).refCount().firstOrError()
}


