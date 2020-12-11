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

class Seats(private val seats: Array<CharArray>) {
    fun count(c: Char): Int = seats.sumBy { it.count { value -> value == c } }

    companion object {
        fun sized(width: Int, height: Int): Seats = Seats(Array(height) { CharArray(width) })
    }

    private val width: Int
        get() = seats[0].size
    val height = seats.size

    private val yRange = seats.indices
    private val xRange: IntRange
        get() = seats[0].indices

    private val indices: List<Point>
        get() = xRange.flatMap { x -> yRange.map { y -> Point(x, y) } }

    override operator fun equals(other: Any?): Boolean = other is Seats && seats.contentDeepEquals(other.seats)
    override fun hashCode(): Int = seats.contentDeepHashCode()
    operator fun contains(point: Point) = point.x in xRange && point.y in yRange

    operator fun get(it: Point): Char = seats[it.y][it.x]
    operator fun set(it: Point, char: Char) {
        seats[it.y][it.x] = char
    }

    fun simulateUntilStable(calculateSeat: (Seats, Point) -> Char): Seats {
        var new = this
        var previousSeats = sized(0, 0)
        while (new != previousSeats) {
            previousSeats = new
            new = run {
                val newSeats = sized(previousSeats.width, previousSeats.height)
                for (point in newSeats.indices) {
                    newSeats[point] = calculateSeat(previousSeats, point)
                }
                newSeats
            }
        }
        return new
    }

    fun calculateSeatA(point: Point): Char {
        val current = this[point]
        val adjacent = directions.map { point + it }
            .filter { it in this }
            .map { this[it] }

        return when {
            current == 'L' && adjacent.count { it == '#' } == 0 -> '#'
            current == '#' && adjacent.count { it == '#' } >= 4 -> 'L'
            else -> this[point]
        }
    }

    fun calculateSeatB(point: Point): Char {
        val currentValue = this[point]

        val seen = directions.mapNotNull {
            pointsInDirection(point, it).firstOrNull { p -> this[p] != '.' }
        }.map { this[it] }

        return when {
            currentValue == 'L' && seen.count { it == '#' } == 0 -> '#'
            currentValue == '#' && seen.count { it == '#' } >= 5 -> 'L'
            else -> this[point]
        }
    }

    private fun pointsInDirection(start: Point, direction: Point): Sequence<Point> =
        generateSequence(start + direction) { it + direction }.takeWhile { it in this }
}

fun solveA(lines: List<String>): Int =
    Seats(lines.map { it.toCharArray() }.toTypedArray())
        .simulateUntilStable(Seats::calculateSeatA)
        .count('#')

fun solveB(lines: List<String>): Int =
    Seats(lines.map { it.toCharArray() }.toTypedArray())
        .simulateUntilStable(Seats::calculateSeatB)
        .count('#')
