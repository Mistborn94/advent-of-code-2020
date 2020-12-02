package day3

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class Day3KtTest {


    @Test
    fun sample1() {
        val lines = readDayFile(3, "sample1.in").readLines()

        assertEquals(0, solveA(lines))
        assertEquals(0, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(3, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(0, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(0, solveB)
    }
}