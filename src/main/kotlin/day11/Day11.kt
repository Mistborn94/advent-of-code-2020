package day11

import helper.Point

val directions = listOf(
    Point(1, 0),
    Point(-1, 0),
    Point(0, +1),
    Point(0, -1),
    Point(+1, -1),
    Point(-1, -1),
    Point(+1, +1),
    Point(-1, +1)
)

typealias Seats = Array<CharArray>

fun solveA(lines: List<String>): Int {
    return simulateUntilStable(lines) { simulateA(it) }
}

private fun simulateUntilStable(lines: List<String>, simulate: (Seats) -> Seats): Int {
    var seats = lines.map { it.toCharArray() }.toTypedArray()
    var previousSeats: Seats = Array(0) { CharArray(0) }
    while (!seats.contentDeepEquals(previousSeats)) {
        previousSeats = seats
        seats = simulate(previousSeats)
    }
    return seats.sumBy { it.count { s -> s == '#' } }
}

fun simulateA(seats: Seats): Seats {
    val newSeats = Array(seats.size) { CharArray(seats[0].size) }

    val yRange = newSeats.indices
    val xRange = newSeats[0].indices
    for (y in yRange) {
        for (x in xRange) {
            val current = seats[y][x]
            val adjacent =
                Point(x, y).neighboursWithDiagonals().filter { it.x in xRange && it.y in yRange }.map { seats[it] }
            if (current == 'L' && adjacent.count { it == '#' } == 0) {
                newSeats[y][x] = '#'
            } else if (current == '#' && adjacent.count { it == '#' } >= 4) {
                newSeats[y][x] = 'L'
            } else {
                newSeats[y][x] = seats[y][x]
            }
        }
    }
    return newSeats;
}

fun simulateB(seats: Array<CharArray>): Array<CharArray> {

    val newSeats = Array(seats.size) { CharArray(seats[0].size) }

    val yRange = newSeats.indices
    val xRange = newSeats[0].indices
    for (y in yRange) {
        for (x in xRange) {
            val current = seats[y][x]
            val point = Point(x, y)
            val seen = directions.map { firstSeenInDirection(point, it, seats) }
                .filter { it in seats }
                .map { seats[it] }
            if (current == 'L' && seen.count { it == '#' } == 0) {
                newSeats[y][x] = '#'
            } else if (current == '#' && seen.count { it == '#' } >= 5) {
                newSeats[y][x] = 'L'
            } else {
                newSeats[y][x] = seats[y][x]
            }
        }
    }
    return newSeats;
}

fun firstSeenInDirection(start: Point, direction: Point, seats: Array<CharArray>): Point {

    var point = start + direction

    while (point in seats && seats[point] == '.') {
        point += direction
    }
    return point
}


private operator fun Array<CharArray>.contains(point: Point) =
    point.x in this[0].indices && point.y in indices

private operator fun Array<CharArray>.get(it: Point): Char = this[it.y][it.x]

fun solveB(lines: List<String>): Int {
    var seats = lines.map { it.toCharArray() }.toTypedArray()
    var previousSeats = Array(0) { CharArray(0) }
    while (!seats.contentDeepEquals(previousSeats)) {
        previousSeats = seats
        seats = simulateB(previousSeats)
    }
    return seats.sumBy { it.count { s -> s == '#' } }
}
