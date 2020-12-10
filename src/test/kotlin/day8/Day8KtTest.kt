package day8

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day8KtTest {

    @Test
    fun sample1() {
        val lines = readDayFile(8, "sample1.in").readLines()

        assertEquals(5, solveA(lines))
        assertEquals(8, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(8, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(2034, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(672, solveB)
    }
}