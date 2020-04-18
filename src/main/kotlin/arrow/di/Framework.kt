@file:Suppress("unused")

package arrow.di

import arrow.typeclasses.DaoDatabase
import arrow.typeclasses.Index
import arrow.typeclasses.UserDao
import io.reactivex.rxjava3.core.Single

interface NetworkOperations {
    val network: NetworkModule

    fun Index.requestUser(): Single<UserDto> = network.fetch(this, mapOf("1" to "2"))
}

interface DaoOperations {
    val dao: DaoDatabase

    fun Index.queryUser(): Single<UserDao> =
        dao.query("SELECT * from Users where userId = $this")

    fun Index.queryCompany(): Single<UserDao> =
        dao.query("SELECT * from Companies where companyId = $this")
}