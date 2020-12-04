package day4

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class Day4KtTest {

    @Test
    fun sample1() {
        val lines = readDayFile(4, "sample1.in").readText()

        assertEquals(2, solveA(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(4, "sample2.in").readText()

        assertEquals(4, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(4, "input").readText()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(235, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(194, solveB)
    }
}