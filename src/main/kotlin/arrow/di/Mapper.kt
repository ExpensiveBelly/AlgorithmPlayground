package arrow.di

import arrow.typeclasses.User
import arrow.typeclasses.UserDao
import arrow.typeclasses.realWorld
import io.reactivex.rxjava3.core.Single

interface DomainMapper {

    fun Single<UserDto>.toUserFromNetwork(): Single<User> =
        map { user -> realWorld { User(user.id) } }

    fun Single<UserDao>.toUserFromDatabase(): Single<User> =
        map { user -> realWorld { User(user.id) } }
}