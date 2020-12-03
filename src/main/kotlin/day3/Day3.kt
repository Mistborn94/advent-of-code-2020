package day3

import helper.Point


fun solveA(lines: List<String>): Int {
    val slope = Point(3, 1)

    val cells = lines.map { it.toCharArray().asList() }
    return treeCount(slope, cells)
}

private fun treeCount(slope: Point, cells: List<List<Char>>): Int {
    val width = cells[0].size
    operator fun List<List<Char>>.get(point: Point) = this[point.y][point.x % width]

    var position = Point(0, 0)
    var treeCount = 0

    while (position.y < cells.size) {
        if (cells[position] == '#') {
            treeCount++
        }
        position += slope
    }
    return treeCount
}


fun solveB(lines: List<String>): Long {

    val cells = lines.map { it.toCharArray().asList() }
    return listOf(
            Point(1, 1),
            Point(3, 1),
            Point(5, 1),
            Point(7, 1),
            Point(1, 2),
    )
            .map { treeCount(it, cells) }
            .map { it.toLong() }
            .reduce { acc, i -> acc * i }

}


