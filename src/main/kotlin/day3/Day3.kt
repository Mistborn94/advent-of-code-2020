package day3

import helper.Point


fun solveA(lines: List<String>): Int = treeCount(lines, Point(3, 1))

private fun treeCount(cells: List<String>, slope: Point): Int {
    val width = cells[0].length

    return (cells.indices step slope.y)
        .mapIndexed { index, y -> Point(index * slope.x, y) }
        .count { cells[it.y][it.x % width] == '#' }
}

fun solveB(lines: List<String>): Long = listOf(Point(1, 1), Point(3, 1), Point(5, 1), Point(7, 1), Point(1, 2))
    .map { treeCount(lines, it) }
    .map { it.toLong() }
    .reduce { acc, i -> acc * i }


