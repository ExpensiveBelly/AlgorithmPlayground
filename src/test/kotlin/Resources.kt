import algorithm.LinkedListInput
import com.google.gson.Gson
import java.io.File
import java.net.URI

object Resources {

    fun fromJsonFile(fileName: String): Triple<List<Int>, List<Int>, List<Int>> {
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val readText = File(Resources.javaClass.classLoader.getResource(fileName).toURI()).readText()
        val fromJson = Gson().fromJson<LinkedListInput>(readText, LinkedListInput::class.java)

        return Triple(fromJson.input.a.toList(), fromJson.input.b.toList(), fromJson.output.toList())
    }

    fun resourceAsString(fileName: String, delimiter: String = ""): String =
        resourceAsList(fileName).reduce { a, b -> "$a$delimiter$b" }

    fun resourceAsList(fileName: String): List<String> =
        File(fileName.toURI()).readLines()

    private fun String.toURI(): URI =
        Resources.javaClass.classLoader.getResource(this)?.toURI()
            ?: throw IllegalArgumentException("Cannot find Resource: $this")
}