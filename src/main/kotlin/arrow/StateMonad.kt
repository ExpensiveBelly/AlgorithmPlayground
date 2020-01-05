package arrow

import arrow.core.ForId
import arrow.core.Id
import arrow.core.Tuple2
import arrow.core.extensions.id.monad.monad
import arrow.mtl.State
import arrow.mtl.StateT
import arrow.mtl.extensions.fx

/**
 * https://jorgecastillo.dev/state-monad
 */
data class Account(val id: String, val number: Long, var balance: Double)

private class FirstIteration {

    data class Bank(val name: String, val swiftCode: String, val accounts: List<Account> = listOf()) {

        fun getAccountBalance(accountId: String): Double =
            accounts.find { it.id == accountId }?.balance ?: 0.0

        fun withdraw(accountId: String, amount: Double): Double {
            val account = accounts.find { it.id == accountId }
            return if (account != null && account.balance >= amount) {
                account.balance -= amount
                amount
            } else {
                0.0
            }
        }

        fun deposit(accountId: String, amount: Double): Double {
            val account = accounts.find { it.id == accountId }
            return if (account != null) {
                account.balance += amount
                amount
            } else {
                0.0
            }
        }
    }

    private fun transferHalfOfTheMoney(bank: Bank, from: String, to: String): Double {
        val balance = bank.getAccountBalance(from)
        val withdrawn = bank.withdraw(from, balance / 2)
        return bank.deposit(to, withdrawn)
    }

    fun main() {
        println("First iteration")
        val bank = Bank(name = "Central Bank", swiftCode = "CBESMMX")
        val transferred = transferHalfOfTheMoney(bank, from = "101931", to = "101932")
        println(transferred)
    }
}

class SecondIteration {

    data class Bank(val name: String, val swiftCode: String, val accounts: List<Account> = listOf())

    fun getAccountBalance(bank: Bank, accountId: String): Tuple2<Bank, Double> =
        Tuple2(bank, bank.accounts.find { it.id == accountId }?.balance ?: 0.0)

    fun withdraw(bank: Bank, accountId: String, amount: Double): Tuple2<Bank, Double> {
        val account = bank.accounts.find { it.id == accountId }
        return Tuple2(
            bank, if (account != null && account.balance >= amount) {
                account.balance -= amount
                amount
            } else {
                0.0
            }
        )
    }

    fun deposit(bank: Bank, accountId: String, amount: Double): Tuple2<Bank, Double> {
        val account = bank.accounts.find { it.id == accountId }
        return Tuple2(
            bank, if (account != null) {
                account.balance += amount
                amount
            } else {
                0.0
            }
        )
    }
}

class ThirdIteration {

    data class Account(val id: String, val number: Long, val balance: Double)

    data class Bank(val name: String, val swiftCode: String, val accounts: List<Account> = listOf())

    fun getAccountBalance(bank: Bank, accountId: String): Tuple2<Bank, Double> =
        Tuple2(bank, bank.accounts.find { it.id == accountId }?.balance ?: 0.0)

    fun withdraw(bank: Bank, accountId: String, amount: Double): Tuple2<Bank, Double> {
        val account = bank.accounts.find { it.id == accountId }
        return if (account != null && account.balance >= amount) {
            Tuple2(
                bank.copy(accounts = bank.accounts.map { acc ->
                    if (acc.id == accountId) {
                        acc.copy(balance = acc.balance - amount)
                    } else {
                        acc
                    }
                }),
                amount
            )
        } else {
            Tuple2(bank, 0.0)
        }
    }

    fun deposit(bank: Bank, accountId: String, amount: Double): Tuple2<Bank, Double> {
        val account = bank.accounts.find { it.id == accountId }
        return if (account != null) {
            Tuple2(
                bank.copy(accounts = bank.accounts.map { acc ->
                    if (acc.id == accountId) {
                        acc.copy(balance = acc.balance + amount)
                    } else {
                        acc
                    }
                }),
                amount
            )
        } else {
            Tuple2(bank, 0.0)
        }
    }
}

class FourthIteration {

    data class Bank(val name: String, val swiftCode: String, val accounts: List<ThirdIteration.Account> = listOf())

    fun getAccountBalance(accountId: String) = State<Bank, Double> { bank ->
        Tuple2(bank, bank.accounts.find { it.id == accountId }?.balance ?: 0.0)
    }

    fun withdraw(accountId: String, amount: Double) = State<Bank, Double> { bank ->
        val account = bank.accounts.find { it.id == accountId }
        if (account != null && account.balance >= amount) {
            Tuple2(
                bank.copy(accounts = bank.accounts.map { acc ->
                    if (acc.id == accountId) {
                        acc.copy(balance = acc.balance - amount)
                    } else {
                        acc
                    }
                }),
                amount
            )
        } else {
            Tuple2(bank, 0.0)
        }
    }

    fun deposit(accountId: String, amount: Double) = State<Bank, Double> { bank ->
        val account = bank.accounts.find { it.id == accountId }
        if (account != null) {
            Tuple2(
                bank.copy(accounts = bank.accounts.map { acc ->
                    if (acc.id == accountId) {
                        acc.copy(balance = acc.balance + amount)
                    } else {
                        acc
                    }
                }),
                amount
            )
        } else {
            Tuple2(bank, 0.0)
        }
    }

    fun transferHalfOfTheMoney(from: String, to: String): StateT<ForId, Bank, Double> =
        State.fx(Id.monad()) {
            val balance = getAccountBalance(from).bind()
            val withdrawn = withdraw(from, balance / 2).bind()
            deposit(to, withdrawn).bind()
        }

    fun main() {
        println("Fourth Iteration")
        val bank = Bank(name = "Central Bank", swiftCode = "CBESMMX")
        val transferred = transferHalfOfTheMoney(from = "101931", to = "101932")
            .run(Id.monad(), bank)

        println(transferred)
    }
}

fun main() {
    FirstIteration().main()
    FourthIteration().main()
}