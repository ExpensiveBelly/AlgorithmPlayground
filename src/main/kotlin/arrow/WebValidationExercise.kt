package arrow

import arrow.core.*
import arrow.core.extensions.either.applicative.applicative
import arrow.core.extensions.fx
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.option.applicative.applicative
import arrow.core.extensions.validated.applicative.applicative


private sealed class ValidationError {
    object InvalidMail : ValidationError()
    object InvalidPhoneNumber : ValidationError()
}

private fun String.validatedMail(): Validated<Nel<ValidationError>, String> =
    when {
        validMail(this) -> this.valid()
        else -> ValidationError.InvalidMail.nel().invalid()
    }

private fun String.validatedPhoneNumber(): Validated<Nel<ValidationError>, String> =
    when {
        validNumber(this) -> this.valid()
        else -> ValidationError.InvalidPhoneNumber.nel().invalid()
    }

private fun validMail(mail: String) = false
private fun validNumber(number: String) = false
private fun validateData(mail: String, phoneNumber: String): Validated<Nel<ValidationError>, Pair<String, String>> {
    return Validated.applicative<Nel<ValidationError>>(Nel.semigroup())
        .map(mail.validatedMail(), phoneNumber.validatedPhoneNumber()) {
            Pair(it.a, it.b)
        }.fix()
}

private fun Nel<ValidationError>.handleInvalid() = map {
    handleInvalidField(
        it
    )
}

private fun handleInvalidField(validationError: ValidationError): String =
    when (validationError) {
        ValidationError.InvalidMail -> "Invalid email"
        ValidationError.InvalidPhoneNumber -> "Invalid phone number"
    }

fun main() {
    validateData("abc@gmail.com", "1248573859").fold({ errors ->
        errors.handleInvalid().all.forEach { println(it) }
    }, { (email, phone) -> listOf(email, phone).forEach { println(it) } })

    val fx: Either<ValidationError, Int> = Either.fx<ValidationError, Int> {
        val (a: Int) = Either.Right(1)
        val d: Int = a
        val (b) = Either.Right(1 + a)
        val (c) = Either.Right(1 + b)
        a + b + c
    }

    val tuple = Option.applicative().tupled(Option(1), Option("Hello"), Option(20.0))
    val tuple2 = Validated.applicative(Nel.semigroup<Int>()).tupled(1.valid(), 2.valid())

    val either: Either<NonEmptyList<Int>, Tuple2<Int, Int>> =
        Either.applicative<Nel<Int>>().tupled(Either.Right(1), Either.right(2)).fix()
    Either.applicative<Int>().map(Either.Right(1), Either.Right(2)) { it }.fix()
}