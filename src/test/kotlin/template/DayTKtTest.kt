package template

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
internal class DayTKtTest {

    val day = 0

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(0, solveA(lines))
        assertEquals(0, solveB(lines))
    }

    @Test
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
        assertEquals(0, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(0, solveB)
    }
}