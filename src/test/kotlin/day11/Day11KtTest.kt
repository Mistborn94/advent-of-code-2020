package day11

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day11KtTest {

    val day = 11

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(37, solveA(lines))
        assertEquals(26, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(2281, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(2085, solveB)
    }
}