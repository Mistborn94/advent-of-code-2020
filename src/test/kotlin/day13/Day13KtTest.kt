package day13

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day13KtTest {

    val day = 13

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(295, solveA(lines))
        assertEquals(1068781, solveB(lines))
    }

    //    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readLines()

        assertEquals(0, solveA(lines))
        assertEquals(0, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(3882, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
//        assertEquals(0, solveB)
    }
}