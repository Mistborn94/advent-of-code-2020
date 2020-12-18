package day18

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day18KtTest {

    private val day = 18

    @Test
    fun samplesA() {

        assertEquals(71, solveA(listOf("1 + 2 * 3 + 4 * 5 + 6")))
        assertEquals(71, solveA(listOf("(1 + 2 * 3) + 4 * 5 + 6")))
        assertEquals(51, solveA(listOf("1 + (2 * 3) + (4 * (5 + 6))")))
        assertEquals(26, solveA(listOf("2 * 3 + (4 * 5)")))
        assertEquals(437, solveA(listOf("5 + (8 * 3 + 9 + 3 * 4 * 3)")))
    }

    @Test
    fun samplesB() {
        assertEquals(231, solveB(listOf("1 + 2 * 3 + 4 * 5 + 6")))
        assertEquals(51, solveB(listOf("1 + (2 * 3) + (4 * (5 + 6))")))
        assertEquals(96, solveB(listOf("8 * 3 + 9")))
        assertEquals(14, solveB(listOf("2 + (3 * 2 + 2)")))
        assertEquals(1440, solveB(listOf("8 * 3 + 9 + 3 * 4 * 3")))
        assertEquals(1445, solveB(listOf("5 + (8 * 3 + 9 + 3 * 4 * 3)")))
        assertEquals(669060, solveB(listOf("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")))
        assertEquals(23340, solveB(listOf("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(1408133923393, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(314455761823725, solveB)
    }
}