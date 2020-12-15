package day15

import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day15KtTest {

    @Test
    fun samples() {

        assertAll(
            { assertEquals(436, solveA("0,3,6")) },
            { assertEquals(1, solveA("1,3,2")) },
            { assertEquals(10, solveA("2,1,3")) },
            { assertEquals(27, solveA("1,2,3")) },
            { assertEquals(78, solveA("2,3,1")) },
            { assertEquals(438, solveA("3,2,1")) },
            { assertEquals(1836, solveA("3,1,2")) }
        )
    }

    @Test
    fun solve() {
        val input = "0,12,6,13,20,1,17"
        val solveA = solveA(input)
        println("A: $solveA")
        assertEquals(620, solveA)

        val solveB = solveB(input)
        println("B: $solveB")
        assertEquals(110871, solveB)
    }
}