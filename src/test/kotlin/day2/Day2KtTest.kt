package day2

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class Day2KtTest {


    @Test
    fun sample1() {
        val lines = readDayFile(2, "sample1.in").readLines()
        assertEquals(2, solveA(lines))
        assertEquals(1, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(2, "input").readLines()
        val solveA = solveA(lines)
        assertEquals(519, solveA)

        val solveB = solveB(lines)
        assertEquals(708, solveB)
    }
}