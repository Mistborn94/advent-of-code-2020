package helper

data class Point(val x: Int, val y: Int) {

    fun abs(): Int {
        return kotlin.math.abs(x) + kotlin.math.abs(y)
    }

    operator fun minus(other: Point): Point =
        Point(x - other.x, y - other.y)

    operator fun plus(other: Point): Point =
        Point(x + other.x, y + other.y)

    fun neighbours() = listOf(
        Point(x + 1, y),
        Point(x - 1, y),
        Point(x, y + 1),
        Point(x, y - 1)
    )

    fun neighboursWithDiagonals() = listOf(
        Point(x + 1, y),
        Point(x - 1, y),
        Point(x, y + 1),
        Point(x, y - 1),
        Point(x + 1, y - 1),
        Point(x - 1, y - 1),
        Point(x + 1, y + 1),
        Point(x - 1, y + 1)
    )

    operator fun <E> List<List<E>>.get(point: Point) = this[point.y][point.x]
    operator fun <E> MutableList<MutableList<E>>.set(point: Point, value: E) {
        this[point.y][point.x] = value
    }
}