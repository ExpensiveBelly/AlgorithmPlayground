package arrow

import arrow.optics.optics

@optics
data class Street(val number: Int, val name: String) {
    companion object
}

@optics
data class Address(val city: String, val street: Street) {
    companion object
}

@optics
data class Company(val name: String, val address: Address) {
    companion object
}

@optics
data class Employee(val name: String, val company: Company) {
    companion object
}

val employee = Employee(
    "John Doe",
    Company(
        "Arrow",
        Address(
            "Functional city",
            Street(23, "lambda street")
        )
    )
)

fun main() {
    println(employee)

    println(
        Employee.company
            .compose(Company.address)
            .compose(Address.street)
            .compose(Street.name)
            .modify(employee, String::capitalize)
    )
}
