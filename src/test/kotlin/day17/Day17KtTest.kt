package day17

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

//@Ignore
internal class Day17KtTest {

    private val day = 17

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(112, solveA(lines))
        assertEquals(848, solveB(lines))
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
        assertEquals(315, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
//        assertEquals(0, solveB)
    }
}