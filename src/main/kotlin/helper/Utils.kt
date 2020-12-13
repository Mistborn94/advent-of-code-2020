package helper

import java.util.*
import java.util.concurrent.BlockingQueue

fun <T> ArrayList<T>.resize(minimumSize: Int, supplier: () -> T) {
    if (minimumSize < 0) {
        throw IllegalArgumentException("Negative sizes not allowed")
    }
    ensureCapacity(minimumSize)
    while (size < minimumSize) {
        add(supplier())
    }
}

fun <T> BlockingQueue<T>.drainToList(): List<T> {
    val outputList = mutableListOf<T>()
    drainTo(outputList)
    return outputList
}

fun <T> List<List<T>>.indexOf(item: T): Point {
    val startingY = this.indexOfFirst { it.contains(item) }
    return Point(this[startingY].indexOf(item), startingY)
}

private val gcdMap = mutableMapOf<Pair<Long, Long>, Long>()

fun leastCommonMultiple(a: Long, b: Long): Long = (a * b) / greatestCommonDivisor(a, b)

fun greatestCommonDivisor(a: Long, b: Long): Long {
    val aIsEven = a % 2 == 0L
    val bIsEven = b % 2 == 0L
    return gcdMap.getOrPut(minOf(a, b) to maxOf(a, b)) {
        when {
            a == 0L -> b
            b == 0L -> a
            aIsEven && bIsEven -> greatestCommonDivisor(a / 2, b / 2) * 2
            aIsEven -> greatestCommonDivisor(a / 2, b)
            bIsEven -> greatestCommonDivisor(a, b / 2)
            else -> {
                val first = maxOf(a, b)
                val second = minOf(a, b)
                greatestCommonDivisor(second, first % second)
            }
        }
    }
}