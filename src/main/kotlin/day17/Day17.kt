package day17

import helper.HyperspacePoint
import helper.HyperspaceTrie
import helper.mapToInt

fun solveA(lines: List<String>): Int {
    return ConwayCubes(lines).solveA()
}

fun solveB(lines: List<String>): Int {
    return ConwayCubes(lines).solveB()
}

class ConwayCubes(val initial: List<String>) {

    lateinit var lowerBounds: IntArray
    lateinit var upperBounds: IntArray
    lateinit var hyperspace: HyperspaceTrie<State>

    fun solveA(): Int {
        init()
        repeat(6) { this.iterateA() }

        return hyperspace.count { it == State.ACTIVE }
    }

    private fun iterateA() {
        lowerBounds = lowerBounds.mapToInt { it - 1 }
        upperBounds = upperBounds.mapToInt { it + 1 }
        val newHyperspace = HyperspaceTrie<State>(lowerBounds, upperBounds)

        iterateInner(0, newHyperspace)
        hyperspace = newHyperspace
    }

    fun solveB(): Int {
        init()
        repeat(6) { this.iterateB() }
        return hyperspace.count()
    }

    private fun iterateB() {
        lowerBounds = lowerBounds.mapToInt { it - 1 }
        upperBounds = upperBounds.mapToInt { it + 1 }
        val newHyperspace = HyperspaceTrie<State>(lowerBounds, upperBounds)

        (lowerBounds[0]..upperBounds[0]).forEach { w ->
            iterateInner(w, newHyperspace)
        }
        hyperspace = newHyperspace
    }

    private fun init() {
        lowerBounds = intArrayOf(0, 0, 0, 0)
        upperBounds = intArrayOf(0, 0, initial.lastIndex, initial[0].lastIndex)
        hyperspace = HyperspaceTrie(lowerBounds, upperBounds)

        initial.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == '#') {
                    hyperspace[intArrayOf(0, 0, y, x)] = State.ACTIVE
                }
            }
        }
    }

    private fun iterateInner(w: Int, newHyperspace: HyperspaceTrie<State>) {
        (lowerBounds[1]..upperBounds[1]).forEach { z ->
            (lowerBounds[2]..upperBounds[2]).forEach { y ->
                (lowerBounds[3]..upperBounds[3]).forEach { x ->
                    val point = HyperspacePoint(intArrayOf(w, z, y, x))

                    val cell = hyperspace[point.parts] ?: State.INACTIVE
                    val count = point.neighbours
                        .mapNotNull { hyperspace[it.parts] }
                        .count()

                    if ((cell == State.ACTIVE && count in 2..3) || (cell == State.INACTIVE && count == 3)) {
                        newHyperspace[point.parts] = State.ACTIVE
                    }
                }
            }
        }
    }
}

enum class State(private val char: Char) {
    ACTIVE('#'),
    INACTIVE('.');

    override fun toString(): String = char.toString()
}

