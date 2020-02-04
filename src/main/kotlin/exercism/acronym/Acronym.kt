package exercism.acronym

object Acronym {
    fun generate(phrase: String): String {
        return phrase
            .replace("-", " ")
            .replace("_", "")
            .trim()
            .split(" ").also { println(it) }
            .mapNotNull { it.firstOrNull() }
            .joinToString(separator = "")
            .toUpperCase()
    }
}
