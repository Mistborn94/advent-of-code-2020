package day22

import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore
internal class Day22KtTest {

    private val day = 22

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(0, day25.solveA(lines))
        assertEquals(0, day25.solveB(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readLines()

        assertEquals(0, day25.solveA(lines))
        assertEquals(0, day25.solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = day25.solveA(lines)
        println("A: $solveA")
        assertEquals(0, solveA)

        val solveB = day25.solveB(lines)
        println("B: $solveB")
        assertEquals(0, solveB)
    }
}