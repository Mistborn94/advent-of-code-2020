package day24

import helper.HyperspacePoint
import helper.collections.IntTrie
import helper.mapToInt
import java.util.*

/**
 * Options for hexagon coordinate systems:
 * https://math.stackexchange.com/questions/2254655/hexagon-grid-coordinate-system
 */
enum class Direction(val vector: HyperspacePoint) {
    EAST(HyperspacePoint.of(1, -1)),
    WEST(HyperspacePoint.of(-1, 1)),

    NORTH_WEST(HyperspacePoint.of(0, 1)),
    SOUTH_EAST(HyperspacePoint.of(0, -1)),

    NORTH_EAST(HyperspacePoint.of(1, 0)),
    SOUTH_WEST(HyperspacePoint.of(-1, 0));

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

fun solveA(lines: List<String>): Int = applyInstructions(lines).size

private fun applyInstructions(lines: List<String>): MutableSet<HyperspacePoint> {
    val instructions = lines.map { toDirections(it) }

    val blackHexagons = mutableSetOf<HyperspacePoint>()
    instructions.forEach {
        val hexagonPoint = it.fold(HyperspacePoint.zero(3)) { point, direction -> point + direction.vector }

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
        val direction = when (val first = chars.poll()) {
            'w', 'e' -> Direction.ofString("$first")
            else -> Direction.ofString("$first${chars.poll()}")
        }
        directions.add(direction)
    }
    return directions
}


fun solveB(lines: List<String>): Int {
    val blackHexagons = applyInstructions(lines)

    val lowerBounds = blackHexagons.reduce(HyperspacePoint::minOfComponents).parts
    val upperBounds = blackHexagons.reduce(HyperspacePoint::maxOfComponents).parts

    return (1..100).fold(intialTrie(lowerBounds, upperBounds, blackHexagons)) { previous, it ->
        val newLower = previous.lowerBounds.mapToInt { it - 1 }
        val newUpper = previous.upperBounds.mapToInt { it + 1 }
        val newTrie = IntTrie.create(newLower, newUpper)

        (newLower[0]..newUpper[0]).forEach { x ->
            (newLower[1]..newUpper[1]).forEach { y ->
                val point = HyperspacePoint.of(x, y)
                val black = previous.contains(point.parts)

                val blackNeighbours = Direction.values()
                    .count { previous.contains(point + it.vector) }

                if (black && blackNeighbours in 1..2
                    || !black && blackNeighbours == 2
                ) {
                    newTrie.add(point)
                }
            }
        }

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
