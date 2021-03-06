package day13

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day13KtTest {

    private val day = 13

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(295, solveA(lines))
        assertEquals(1068781, solveB(lines[1], 1000000))
        assertEquals(1068781, solveBAlternate(lines[1]))
    }

    @Test
    fun sample2() {
        assertEquals(754018, solveB("67,7,59,61"))
        assertEquals(754018, solveBAlternate("67,7,59,61"))

        assertEquals(779210, solveB("67,x,7,59,61"))
        assertEquals(779210, solveBAlternate("67,x,7,59,61"))

        assertEquals(1261476, solveB("67,7,x,59,61"))
        assertEquals(1261476, solveBAlternate("67,7,x,59,61"))
    }

    @Test
    fun sample3() {
        assertEquals(1202161486, solveB("1789,37,47,1889"))
        assertEquals(1202161486, solveBAlternate("1789,37,47,1889"))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(3882, solveA)

        val solveB = solveBAlternate(lines[1])
        println("B: $solveB")
        assertEquals(867295486378319, solveB)
    }
}