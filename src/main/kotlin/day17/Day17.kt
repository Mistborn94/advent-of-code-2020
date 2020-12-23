package day17

import helper.HyperspacePoint
import helper.collections.IntTrie
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
    lateinit var hyperspace: IntTrie

    fun solveA(): Int {
        init()
        repeat(6) { this.iterateA() }

        return hyperspace.count()
    }

    private fun iterateA() {
        lowerBounds = lowerBounds.mapToInt { it - 1 }
        upperBounds = upperBounds.mapToInt { it + 1 }
        val newHyperspace = IntTrie(lowerBounds, upperBounds)

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
        val newHyperspace = IntTrie(lowerBounds, upperBounds)

        (lowerBounds[0]..upperBounds[0]).forEach { w ->
            iterateInner(w, newHyperspace)
        }
        hyperspace = newHyperspace
    }

    private fun init() {
        lowerBounds = intArrayOf(0, 0, 0, 0)
        upperBounds = intArrayOf(0, 0, initial.lastIndex, initial[0].lastIndex)
        hyperspace = IntTrie(lowerBounds, upperBounds)

        initial.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == '#') {
                    hyperspace.add(intArrayOf(0, 0, y, x))
                }
            }
        }
    }

    private fun iterateInner(w: Int, newHyperspace: IntTrie) {
        (lowerBounds[1]..upperBounds[1]).forEach { z ->
            (lowerBounds[2]..upperBounds[2]).forEach { y ->
                (lowerBounds[3]..upperBounds[3]).forEach { x ->
                    val point = HyperspacePoint(intArrayOf(w, z, y, x))

                    val cell = if (hyperspace.contains(point.parts)) ACTIVE else INACTIVE
                    val count = hyperspace.countWithNeighbours(point.parts) - cell

                    if ((cell == ACTIVE && count in 2..3) || (cell == INACTIVE && count == 3)) {
                        newHyperspace.add(point.parts)
                    }
                }
            }
        }
    }

    companion object {
        private const val ACTIVE = 1
        private const val INACTIVE = 0
    }
}

