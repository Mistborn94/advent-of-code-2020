package day24

import helper.HyperspacePoint
import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals


internal class Day24KtTest {

    private val day = 24

    @Test
    fun sample1() {
        val lines = readDayFile(day, "sample1.in").readLines()

        assertEquals(10, solveA(lines))
        assertEquals(2208, solveB(lines))
    }

    @Test
    fun sample2() {
        val lines = "nwwswee".lines()

        assertEquals(1, solveA(lines))
        assertEquals(0, solveA(lines + lines))
    }

    @Test
    fun sample3() {
        val lines1 = """
            |nwwswe
            |wnwse
        """.trimMargin().lines()

        val lines2 = """
            |ew
            |we
        """.trimMargin().lines()

        assertEquals(0, solveA(lines1))
        assertEquals(0, solveA(lines2))

//        assertEquals(0, solveB(lines))
    }

    @Test
    fun testDirections() {
        val zeroPoint = HyperspacePoint.of(0, 0, 0)
        assertSum(zeroPoint, listOf(Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.WEST))
        assertSum(zeroPoint, listOf(Direction.NORTH_EAST, Direction.WEST, Direction.SOUTH_EAST))
        assertSum(
            zeroPoint,
            listOf(
                Direction.NORTH_WEST,
                Direction.SOUTH_WEST,
                Direction.SOUTH_EAST,
                Direction.EAST,
                Direction.NORTH_EAST,
                Direction.NORTH_WEST,
                Direction.WEST,
                Direction.SOUTH_EAST
            )
        )

    }

    private fun assertSum(point: HyperspacePoint, directions: List<Direction>) {
        assertEquals(point, directions.fold(HyperspacePoint.of(0, 0, 0)) { acc, direction -> acc + direction.vector })
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readLines()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(394, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(4036, solveB)
    }
}