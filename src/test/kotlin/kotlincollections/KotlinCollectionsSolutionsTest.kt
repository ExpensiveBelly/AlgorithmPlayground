package kotlincollections

import junit.framework.TestCase.assertEquals
import kotlincollections.KotlinCollectionsSolutions.Person
import kotlincollections.KotlinCollectionsSolutions.Student
import org.junit.Before
import org.junit.Test

class KotlinCollectionsSolutionsTest {

    private lateinit var exercises: KotlinCollectionsSolutions

    @Before
    fun setUp() {
        exercises = KotlinCollectionsSolutions()
    }

    @Test
    fun `accumulate names in a list`() {
        assertEquals(listOf("David", "Max", "Peter", "Pamela"), exercises.`accumulate names in a list`())
    }

    @Test
    fun `find people of legal age, output formatted string`() {
        assertEquals(
            "In Germany Max and Peter and Pamela are of legal age.",
            exercises.`find people of legal age, output formatted string`()
        )
    }

    @Test
    fun `group people by age, print age and names together`() {
        assertEquals(
            mapOf(18 to "Max", 23 to "Peter;Pamela", 12 to "David"),
            exercises.`group people by age, print age and names together`()
        )
    }

    @Test
    fun `lazily iterate Doubles, map to Int, map to String, print each`() {
        assertEquals("a1, a2, a3", exercises.`lazily iterate Doubles, map to Int, map to String, print each`())
    }

    @Test
    fun `counting items in a list after filter is applied`() {
        assertEquals(2, exercises.`counting items in a list after filter is applied`())
    }

    @Test
    fun `convert elements to strings and concatenate them, separated by commas`() {
        assertEquals(
            "laptop, monitor, mouse, keyboard",
            exercises.`convert elements to strings and concatenate them, separated by commas`()
        )
    }

    @Test
    fun `compute sum of salaries of employee`() {
        assertEquals(10_000, exercises.`compute sum of salaries of employee`())
    }

    @Test
    fun `compute sum of salaries by department`() {
        assertEquals(
            mapOf("IT" to 3_000, "Management" to 5_000, "HR" to 2_000),
            exercises.`compute sum of salaries by department`()
        )
    }

    @Test
    fun `partition students into passing and failing`() {
        assertEquals(
            Pair(
                listOf(Student(10), Student(5)),
                listOf(Student(3), Student(4))
            ), exercises.`partition students into passing and failing`()
        )
    }

    @Test
    fun `names of male members`() {
        assertEquals(listOf("David", "Max", "Peter"), exercises.`names of male members`())
    }

    @Test
    fun `group names of members in roster by gender`() {
        assertEquals(
            mapOf(
                Person.Sex.MALE to listOf("David", "Max", "Peter"),
                Person.Sex.FEMALE to listOf("Pamela")
            ),
            exercises.`group names of members in roster by gender`()
        )
    }

    @Test
    fun `filter a list to another list`() {
        assertEquals(emptyList<String>(), exercises.`filter a list to another list with items that start with o`())
    }

    @Test
    fun `finding shortest string a list`() {
        assertEquals("tray", exercises.`finding shortest string a list`())
    }

    @Test
    fun `iterate an array, map the values, calculate the average`() {
        assertEquals(5.0, exercises.`iterate an array, map the values, calculate the average`())
    }

    @Test
    fun `lazily iterate a list of strings, map the values, convert to Int, find max`() {
        assertEquals(3, exercises.`lazily iterate a list of strings, map the values, convert to Int, find max`())
    }

    @Test
    fun `filter, upper case, then sort a list`() {
        assertEquals(listOf("C1", "C2"), exercises.`filter, upper case, then sort a list`())
    }

    @Test
    fun `map names, join together with delimiter`() {
        assertEquals("DAVID | MAX | PETER | PAMELA", exercises.`map names, join together with delimiter`())
    }

    @Test
    fun `reorder person list according to new ids`() {
        assertEquals(
            listOf(
                Person(id = 4, name = "Pamela", age = 23, gender = Person.Sex.FEMALE),
                Person(id = 2, name = "Max", age = 18, gender = Person.Sex.MALE),
                Person(id = 3, name = "Peter", age = 23, gender = Person.Sex.MALE),
                Person(id = 1, name = "David", age = 12, gender = Person.Sex.MALE)
            )
            , exercises.`reorder person list according to person_ids`(listOf(4, 2, 3, 1))
        )

        assertEquals(
            listOf(
                Person(id = 3, name = "Peter", age = 23, gender = Person.Sex.MALE),
                Person(id = 4, name = "Pamela", age = 23, gender = Person.Sex.FEMALE),
                Person(id = 1, name = "David", age = 12, gender = Person.Sex.MALE),
                Person(id = 2, name = "Max", age = 18, gender = Person.Sex.MALE)
            )
            , exercises.`reorder person list according to person_ids`(listOf(3, 4, 1, 2))
        )
    }
}