package day12

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day12KtTest {

    val day = 12

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(25, solveA(lines))
        assertEquals(286, solveB(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readLines()

        assertEquals(0, solveA(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(1533, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(25235, solveB)
    }
}