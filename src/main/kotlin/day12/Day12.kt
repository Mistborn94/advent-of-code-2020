package day12

import day12.Direction.*
import helper.Point

enum class Direction(val point: Point) {

    NORTH(Point(0, 1)) {
        override val right: Direction
            get() = EAST
        override val left: Direction
            get() = WEST
    },
    EAST(Point(1, 0)) {
        override val right: Direction
            get() = SOUTH
        override val left: Direction
            get() = NORTH
    },
    SOUTH(Point(0, -1)) {
        override val right: Direction
            get() = WEST
        override val left: Direction
            get() = EAST
    },
    WEST(Point(-1, 0)) {
        override val right: Direction
            get() = NORTH
        override val left: Direction
            get() = SOUTH
    };

    abstract val right: Direction
    abstract val left: Direction
}

fun solveA(lines: List<String>): Int {
    var currentPosition = Point(0, 0)
    var currentDirection = EAST

    lines.forEach { line ->
        val action = line[0]
        val value = line.substring(1, line.length).toInt()
        when (action) {
            'N' -> currentPosition += NORTH.point * value
            'S' -> currentPosition += SOUTH.point * value
            'E' -> currentPosition += EAST.point * value
            'W' -> currentPosition += WEST.point * value
            'L' -> repeat(value / 90) {
                currentDirection = currentDirection.left
            }
            'R' -> repeat(value / 90) {
                currentDirection = currentDirection.right
            }
            'F' -> currentPosition += currentDirection.point * value
        }

    }
    return currentPosition.abs()
}

fun solveB(lines: List<String>): Int {
    var waypointPosition = Point(10, 1)
    var shipPosition = Point(0, 0)

    lines.forEach { line ->
        val action = line[0]
        val value = line.substring(1, line.length).toInt()
        when (action) {
            'N' -> waypointPosition += NORTH.point * value
            'S' -> waypointPosition += SOUTH.point * value
            'E' -> waypointPosition += EAST.point * value
            'W' -> waypointPosition += WEST.point * value
            'L' -> waypointPosition = waypointPosition.counterClockwise(value)
            'R' -> waypointPosition = waypointPosition.clockwise(value)
            'F' -> shipPosition += waypointPosition * value
        }

    }
    return shipPosition.abs()
}