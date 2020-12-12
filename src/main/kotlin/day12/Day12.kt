package day12

import helper.Direction.*
import helper.Point

fun solveA(lines: List<String>): Int =
    lines.fold(Pair(EAST, Point(0, 0))) { (direction, position), line ->
        val action = line[0]
        val value = line.substring(1, line.length).toInt()

        when (action) {
            'N' -> Pair(direction, position + NORTH.point * value)
            'S' -> Pair(direction, position + SOUTH.point * value)
            'E' -> Pair(direction, position + EAST.point * value)
            'W' -> Pair(direction, position + WEST.point * value)
            'L' -> Pair(direction.left(value / 90), position)
            'R' -> Pair(direction.right(value / 90), position)
            'F' -> Pair(direction, position + direction.point * value)
            else -> throw UnsupportedOperationException()
        }
    }.second.abs()

fun solveB(lines: List<String>): Int =
    lines.fold(Pair(Point(0, 0), Point(10, 1))) { (ship, waypoint), line ->
        val action = line[0]
        val value = line.substring(1, line.length).toInt()
        when (action) {
            'N' -> Pair(ship, waypoint + NORTH.point * value)
            'S' -> Pair(ship, waypoint + SOUTH.point * value)
            'E' -> Pair(ship, waypoint + EAST.point * value)
            'W' -> Pair(ship, waypoint + WEST.point * value)
            'L' -> Pair(ship, waypoint.counterClockwise(value))
            'R' -> Pair(ship, waypoint.clockwise(value))
            'F' -> Pair(ship + waypoint * value, waypoint)
            else -> throw UnsupportedOperationException()
        }
    }.first.abs()