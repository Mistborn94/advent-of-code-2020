package day14

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day14KtTest {

    val day = 14

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(165, solveA(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readLines()

        assertEquals(208, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(17934269678453, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(3440662844064, solveB)
    }
}