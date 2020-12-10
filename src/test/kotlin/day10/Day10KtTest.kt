package day10

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class Day10KtTest {

    val day = 10

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(35, solveA(lines))
        assertEquals(8, solveB(lines))
    }

    @Test
    fun sample2() {
        val lines = readDayFile(day, "sample2.in").readLines()

        assertEquals(220, solveA(lines))
        assertEquals(19208, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(2590, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(226775649501184, solveB)
    }
}