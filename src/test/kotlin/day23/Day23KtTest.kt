package day23

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day23KtTest {

    @Test
    fun sample1() {
        val input = "389125467"

        assertEquals("92658374", solveA(input, 10))
        assertEquals("67384529", solveA(input, 100))
        assertEquals(149245887792, solveB(input))
    }


    @Test
    fun solve() {
        val input = "467528193"

        val solveA = solveA(input)
        println("A: $solveA")
        assertEquals("43769582", solveA)

        val solveB = solveB(input)
        println("B: $solveB")
        assertEquals(264692662390, solveB)
    }
}