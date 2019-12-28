import arrow.core.ValidatedNel
import arrow.core.invalidNel
import arrow.core.valid

fun main() {
    (0..9).map { print('_') }
    println('*')
    (0..9).map {
        (it..9).map { print('_') }
        (0 until it).map { print("$it$it") }
        println('1')
    }
    EmailAddress("hello")
}

data class EmailAddress(val email: String) {

    companion object {

        operator fun invoke(email: String?): ValidatedNel<String, EmailAddress> =
            if (email != null && email.contains("@")) EmailAddress(email).valid()
            else "Email must contain '@' found: '$email'".invalidNel()
    }
}