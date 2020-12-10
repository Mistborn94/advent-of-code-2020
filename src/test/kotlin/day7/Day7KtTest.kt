package day7

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day7KtTest {

    val day = 7

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(4, solveA(lines))
        assertEquals(32, solveB(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readLines()

        assertEquals(126, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(213, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(38426, solveB)
    }
}