package day16

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals


internal class Day16KtTest {

    private val day = 16

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(71, solveA(lines))
//        assertEquals(0, solveB(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readLines()

//        assertEquals(0, solveA(lines))
        assertEquals(13, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(26988, solveA)
//
        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(426362917709, solveB)
    }
}