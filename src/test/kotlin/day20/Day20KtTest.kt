package day20

import helper.readDayFile
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day20KtTest {

    private val day = 20

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readText()

        assertEquals(20899048083289, solveA(lines))
        assertEquals(273, solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(108603771107737, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(2129, solveB)
    }
}