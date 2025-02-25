package exercism.acronym

import java.util.Locale

object Acronym {
    fun generate(phrase: String): String {
        return phrase
            .replace("-", " ")
            .replace("_", "")
            .trim()
            .split(" ").also { println(it) }
            .mapNotNull { it.firstOrNull() }
            .joinToString(separator = "")
            .uppercase(Locale.getDefault())
    }
}
