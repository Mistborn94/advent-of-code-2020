package day5

import helper.readDayFile
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class Day5KtTest {

    @Test
    fun sample1() {
        assertEquals(357, solveA("FBFBBFFRLR"))
        assertEquals(567, solveA("BFFFBBFRRR"))
        assertEquals(119, solveA("FFFBBBFRRR"))
        assertEquals(820, solveA("BBFFBBFRLL"))
    }

    @Test
    fun solve() {
        val lines = readDayFile(5, "input").readText().trim()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(965, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(524, solveB)
    }
}