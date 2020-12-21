package day21

import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day21KtTest {
    private val day = 21

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(5, solveA(lines))
        assertEquals("mxmxvkd,sqjhc,fvjkl", solveB(lines))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(2517, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals("rhvbn,mmcpg,kjf,fvk,lbmt,jgtb,hcbdb,zrb", solveB)
    }
}