package day19

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day19KtTest {

    private val day = 19

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readText()

        assertEquals(2, solveA(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readText()

        assertEquals(3, solveA(lines))
        assertEquals(12, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(184, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(389, solveB)
    }
}