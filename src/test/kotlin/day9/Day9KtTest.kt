package day9

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day9KtTest {

    @Test
    fun sample1() {
        val lines = readDayFile(9, "sample1.in").readLines()

        assertEquals(127, solveA(lines, 5))
        assertEquals(62, solveB(lines, 127))
    }

    @Test
    fun solve() {
        val lines = readDayFile(9, "input").readLines()

        val solveA = solveA(lines, 25)
        println("A: $solveA")
        assertEquals(248131121, solveA)

        val solveB = solveB(lines, solveA)
        println("B: $solveB")
        assertEquals(31580383, solveB)
    }
}