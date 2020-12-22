package day22

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day22KtTest {

    private val day = 22

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readText()

        assertEquals(306, solveA(lines))
        assertEquals(291, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(31308, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(33647, solveB)
    }
}