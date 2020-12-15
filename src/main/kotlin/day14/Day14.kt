package day14

import kotlin.math.pow

class Mask(private val orMask: Long, private val andMask: Long) {

    constructor(orMask: String, andMask: String) : this(orMask.toLong(2), andMask.toLong(2))

    fun apply(value: Long): Long = value and andMask or orMask
}

fun solveA(lines: List<String>): Long {
    val memory = mutableMapOf<Int, Long>()
    lateinit var mask: Mask

    lines.forEach { line ->
        if (line.startsWith("mask")) {
            val maskPattern = line.substringAfter("mask = ")
            //Sets 1s to 1, ignore 0s
            val orMask = maskPattern.replace("X", "0")
            //Sets 0s to 0, ignore 1s
            val andMask = maskPattern.replace("X", "1")

            mask = Mask(orMask, andMask)
        } else {
            val value = line.substringAfter("=").trim().toLong()
            val address = line.substring(line.indexOf("[") + 1, line.indexOf("]")).toInt()

            memory[address] = mask.apply(value)
        }
    }
    return memory.values.sum()
}


fun solveB(lines: List<String>): Long {

    val memory = mutableMapOf<Long, Long>()
    var masks: List<Mask> = emptyList()

    lines.forEach { line ->
        if (line.startsWith("mask")) {
            val maskPattern = line.substringAfter("mask = ")

            masks = maskPermutations(maskPattern)
        } else {
            val value = line.substringAfter("=").trim().toLong()
            val baseAddress = line.substring(line.indexOf("[") + 1, line.indexOf("]")).toLong()

            masks.forEach { mask ->
                val address = mask.apply(baseAddress)
                memory[address] = value
            }

        }
    }
    return memory.values.sum()
}

fun maskPermutations(baseMask: String): List<Mask> {

    val xIndices = baseMask.mapIndexed { index, char -> Pair(index, char) }
        .filter { (_, char) -> char == 'X' }
        .mapIndexed { xIndex, (stringIndex, _) -> Pair(stringIndex, xIndex) }
        .toMap()

    val xCount = xIndices.size
    val maxXMask = 2.toDouble().pow(xCount).toLong()
    val baseAndMask = "1".repeat(baseMask.length)

    return (0 until maxXMask).map { value ->
        val xValues = value.toString(2).padStart(xCount, '0')
        //Sets 1s but keeps 0s unchanged.
        val orMask = baseMask.toMutableList()
        //Sets 0s but keeps 1s unchanged
        val andMask = baseAndMask.toMutableList()

        xIndices.forEach { (stringIndex, xIndex) ->
            orMask[stringIndex] = xValues[xIndex]
            andMask[stringIndex] = xValues[xIndex]
        }

        Mask(orMask.joinToString(""), andMask.joinToString(""))
    }
}
