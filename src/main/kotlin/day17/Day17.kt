package day17

import helper.Point3D
import helper.Point4D

fun solveA(lines: List<String>): Int {
    return ConwayGridA(lines).solveA()
}


fun solveB(lines: List<String>): Int {
    return ConwayGridB(lines).solveB()
}

class ConwayGridA(initial: List<String>) {

    var firstPoint = Point3D(0, 0, 0)

    //z, y, x
    var layers: List<List<List<State>>>

    val depth: Int get() = layers.size
    val height: Int get() = layers[0].size
    val width: Int get() = layers[0][0].size

    fun blankZ(width: Int, height: Int) = Array(height) { Array(width) { State.INACTIVE }.toMutableList() }.toList()

    init {
        val z0 = initial.map { it.map { char -> if (char == '#') State.ACTIVE else State.INACTIVE } }

        layers = listOf(z0)
    }

    fun solveA(): Int {
        repeat(6) { this.iterateA() }

        return layers.sumBy { layer ->
            layer.sumBy { row -> row.count { it == State.ACTIVE } }
        }
    }

    private fun iterateA() {
        val newLayers = Array(depth + 2) { blankZ(width + 2, height + 2) }.toList()
        val newStart = firstPoint - Point3D(1, 1, 1)

        newLayers.forEachIndexed { zi, layer ->
            val z = zi + newStart.z

            layer.forEachIndexed { yi, row ->
                val y = yi + newStart.y

                row.forEachIndexed { xi, _ ->
                    val x = xi + newStart.x

                    val point = Point3D(x, y, z)
                    val listIndex = point.toListIndex()

                    val cell = if (listIndex in layers) layers[listIndex] else State.INACTIVE
                    val count = listIndex.neighbours
                        .filter { it in layers }
                        .count { layers[it] == State.ACTIVE }

                    if ((cell == State.ACTIVE && count in 2..3) || (cell == State.INACTIVE && count == 3)) {
                        newLayers[zi][yi][xi] = State.ACTIVE
                    }
                }
            }
        }
        firstPoint = newStart
        layers = newLayers
    }

    private fun Point3D.toListIndex() = this - firstPoint

    operator fun List<List<List<State>>>.contains(point: Point3D): Boolean {
        return point.z in this.indices && point.y in this[0].indices && point.x in this[0][0].indices
    }

    operator fun List<List<List<State>>>.get(point3D: Point3D): State {
        return this[point3D.z][point3D.y][point3D.x]
    }
}

class ConwayGridB(initial: List<String>) {

    var firstPoint = Point4D(0, 0, 0, 0)

    //w, z, y, x
    var hyperspace: List<List<List<List<State>>>>

    val hyper: Int get() = hyperspace.size
    val depth: Int get() = hyperspace[0].size
    val height: Int get() = hyperspace[0][0].size
    val width: Int get() = hyperspace[0][0][0].size

    fun blankZ(width: Int, height: Int) = Array(height) { Array(width) { State.INACTIVE }.toMutableList() }.toList()

    init {
        val z0 = initial.map { it.map { char -> if (char == '#') State.ACTIVE else State.INACTIVE } }

        hyperspace = listOf(listOf(z0))
    }

    fun solveB(): Int {
        repeat(6) { this.iterate() }

        return hyperspace.sumBy { layers ->
            layers.sumBy { layer ->
                layer.sumBy { row -> row.count { it == State.ACTIVE } }
            }
        }
    }

    private fun iterate() {
        val newHyperspace = Array(hyper + 2) { Array(depth + 2) { blankZ(width + 2, height + 2) }.toList() }.toList()
        val newStart = firstPoint - Point4D(1, 1, 1, 1)

        newHyperspace.forEachIndexed { wi, layers ->
            val w = wi + newStart.z
            layers.forEachIndexed { zi, layer ->
                val z = zi + newStart.z

                layer.forEachIndexed { yi, row ->
                    val y = yi + newStart.y

                    row.forEachIndexed { xi, _ ->
                        val x = xi + newStart.x

                        val point = Point4D(x, y, z, w)
                        val listIndex = point.toListIndex()

                        val cell = if (listIndex in hyperspace) hyperspace[listIndex] else State.INACTIVE
                        val count = listIndex.neighbours
                            .filter { it in hyperspace }
                            .count { hyperspace[it] == State.ACTIVE }

                        if ((cell == State.ACTIVE && count in 2..3) || (cell == State.INACTIVE && count == 3)) {
                            newHyperspace[wi][zi][yi][xi] = State.ACTIVE
                        }
                    }
                }
            }
        }
        firstPoint = newStart
        hyperspace = newHyperspace
    }

    private fun Point4D.toListIndex() = this - firstPoint

    operator fun List<List<List<List<State>>>>.contains(point: Point4D): Boolean {
        return point.w in this.indices && point.z in this[0].indices && point.y in this[0][0].indices && point.x in this[0][0][0].indices
    }

    operator fun List<List<List<List<State>>>>.get(point: Point4D): State {
        return this[point.w][point.z][point.y][point.x]
    }
}


enum class State(private val char: Char) {
    ACTIVE('#'),
    INACTIVE('.');

    override fun toString(): String = char.toString()

}


