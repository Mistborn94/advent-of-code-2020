package day1

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals


class Day1KtTest {

    @Test
    fun sample1() {
        val lines = readDayFile(1, "sample1.in").readLines()

        val answerA = solveA(lines)
        val answerB = solveB(lines)

        assertEquals(514579, answerA)
        assertEquals(241861950, answerB)
    }

    @Test
    fun solve() {
        val lines = readDayFile(1, "input").readLines()

        val answerA = solveA(lines)

        assertEquals(970816, answerA)
        println("A: $answerA")

        val answerB = solveB(lines)

        assertEquals(96047280, answerB)
        println("B: $answerB")
    }
}