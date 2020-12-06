package day6

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

internal class Day6KtTest {

    val day = 6

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readText()

        assertEquals(11, solveA(lines))
        assertEquals(6, solveB(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readText()

        assertEquals(6630, solveA(lines))
        assertEquals(3437, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(6587, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertTrue(solveB > 3211)
//        assertEquals(0, solveB)
    }
}