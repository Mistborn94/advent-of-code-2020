package day24

import helper.HyperspacePoint
import helper.collections.IntTrie
import helper.mapToInt
import java.util.*

data class Hexagon(val position: HyperspacePoint) {

    var white = true

    fun flip() {
        white = !white
    }
}

enum class Direction(val vector: HyperspacePoint) {
    EAST(HyperspacePoint.of(1, 0, 0)) {
        override val opposite: Direction
            get() = WEST
        override val right: Direction
            get() = SOUTH_EAST
        override val left: Direction
            get() = NORTH_EAST
    },
    SOUTH_EAST(HyperspacePoint.of(0, -1, 1)) {
        override val opposite: Direction
            get() = NORTH_WEST
        override val right: Direction
            get() = SOUTH_WEST
        override val left: Direction
            get() = EAST
    },
    SOUTH_WEST(HyperspacePoint.of(-1, -1, 1)) {
        override val opposite: Direction
            get() = NORTH_EAST
        override val right: Direction
            get() = WEST
        override val left: Direction
            get() = SOUTH_EAST
    },
    WEST(HyperspacePoint.of(-1, 0, 0)) {
        override val opposite: Direction
            get() = EAST
        override val right: Direction
            get() = NORTH_WEST
        override val left: Direction
            get() = SOUTH_WEST
    },
    NORTH_WEST(HyperspacePoint.of(0, 1, -1)) {
        override val opposite: Direction
            get() = SOUTH_EAST
        override val right: Direction
            get() = NORTH_EAST
        override val left: Direction
            get() = WEST
    },
    NORTH_EAST(HyperspacePoint.of(1, 1, -1)) {
        override val opposite: Direction
            get() = SOUTH_WEST
        override val right: Direction
            get() = EAST
        override val left: Direction
            get() = NORTH_WEST
    };

    abstract val opposite: Direction
    abstract val right: Direction
    abstract val left: Direction

    companion object {
        fun ofString(string: String): Direction {
            return when (string) {
                "w" -> WEST
                "e" -> EAST
                "nw" -> NORTH_WEST
                "ne" -> NORTH_EAST
                "sw" -> SOUTH_WEST
                "se" -> SOUTH_EAST
                else -> throw IllegalArgumentException("Unknown direction $string")
            }
        }
    }
}

fun solveA(lines: List<String>): Int {
    val blackHexagons = applyInstructions(lines)

    return blackHexagons.size
}

private fun applyInstructions(lines: List<String>): MutableSet<HyperspacePoint> {
    val instructions = lines.map { toDirections(it) }

    val blackHexagons = mutableSetOf<HyperspacePoint>()
    instructions.forEach {
        val hexagonPoint = it.fold(HyperspacePoint.of(0, 0, 0)) { point, direction -> point + direction.vector }

        if (blackHexagons.contains(hexagonPoint)) {
            blackHexagons.remove(hexagonPoint)
        } else {
            blackHexagons.add(hexagonPoint)
        }
    }
    return blackHexagons
}

fun toDirections(it: String): List<Direction> {
    val directions = mutableListOf<Direction>()
    val chars = LinkedList(it.toList())

    while (!chars.isEmpty()) {
        val first = chars.poll()
        val direction = when (first) {
            'w', 'e' -> Direction.ofString("$first")
            else -> Direction.ofString("$first${chars.poll()}")
        }
        directions.add(direction)
    }
    return directions
}


fun solveB(lines: List<String>): Int {
    val blackHexagons = applyInstructions(lines)

    val lowerBounds = blackHexagons.reduce { a, b ->
        HyperspacePoint.of(minOf(a[0], b[0]), minOf(a[1], b[1]), minOf(a[2], b[2]))
    }.parts

    val upperBounds = blackHexagons.reduce { a, b ->
        HyperspacePoint.of(maxOf(a[0], b[0]), maxOf(a[1], b[1]), maxOf(a[2], b[2]))
    }.parts

    return (1..100).fold(intialTrie(lowerBounds, upperBounds, blackHexagons)) { previous, it ->
        val newLower = previous.lowerBounds.mapToInt { it - 1 }
        val newUpper = previous.upperBounds.mapToInt { it + 1 }
        val newTrie = IntTrie.create(newLower, newUpper)

        (newLower[0]..newUpper[0]).forEach { x ->
            (newLower[1]..newUpper[1]).forEach { y ->
                (newLower[2]..newUpper[2]).forEach { z ->
                    val point = HyperspacePoint.of(x, y, z)
                    val black = previous.contains(point.parts)

                    val blackNeighbours = Direction.values().map { point + it.vector }
                        .count { previous.contains(it.parts) }

                    if (black && blackNeighbours in 1..2
                        || !black && blackNeighbours == 2
                    ) {
                        newTrie.add(point)
                    }
                }
            }
        }

//        println("Completed iteration $it")

        return@fold newTrie
    }.count()
}

private fun intialTrie(
    lowerBounds: IntArray,
    upperBounds: IntArray,
    blackHexagons: MutableSet<HyperspacePoint>
): IntTrie {
    val initialTrie = IntTrie.create(lowerBounds, upperBounds)
    initialTrie.addAll(blackHexagons)
    return initialTrie
}
