package day14

import kotlin.math.pow

class Mask(val orMask: Long, val andMask: Long) {

    fun apply(value: Long): Long = value and andMask or orMask
}

fun solveA(lines: List<String>): Long {
    val memory = mutableMapOf<Int, Long>()
    var mask = ""
    var orMask = 0L
    var andMask = 1L
    lines.forEach { line ->
        if (line.startsWith("mask")) {
            mask = line.substringAfter("mask = ")
            //Sets 1s tp 1
            orMask = mask.replace("X", "0").toLong(2)
            //Sets 0s to 0
            andMask = mask.replace("X", "1").toLong(2)
        } else {
            val value = line.substringAfter("=").trim().toLong()
            val address = line.substring(line.indexOf("[") + 1, line.indexOf("]")).toInt()

            memory[address] = value and andMask or orMask
        }
    }
    return memory.values.sum()
}


fun solveB(lines: List<String>): Long {

    val memory = mutableMapOf<Long, Long>()
    var maskPattern = ""
    var masks: List<Mask> = emptyList();
    lines.forEach { line ->
        if (line.startsWith("mask")) {
            maskPattern = line.substringAfter("mask = ")

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
    val xCount = baseMask.count { it == 'X' }
    val length = baseMask.length
    val max = 2.toDouble().pow(xCount).toLong()

    val xIndices = baseMask.mapIndexed { index, char -> Pair(index, char) }
        .filter { (_, char) -> char == 'X' }
        .mapIndexed { xIndex, (stringIndex, _) -> Pair(stringIndex, xIndex) }
        .toMap()

    //Sets 1s and keeps 0s unchanged.
    //Sets 0s. Unknowns should be 1
    val baseAndMask = "1".repeat(length)

    return (0 until max).map { value ->
        val xMask = value.toString(2).padStart(xCount, '0')
        val orMask = baseMask.toMutableList()
        val andMask = baseAndMask.toMutableList()

        xIndices.forEach { (stringIndex, xIndex) ->
            orMask[stringIndex] = xMask[xIndex]
            andMask[stringIndex] = xMask[xIndex]
        }

        Mask(orMask.joinToString("").toLong(2), andMask.joinToString("").toLong(2))
    }
}
