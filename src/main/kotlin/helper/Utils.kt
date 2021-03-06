package helper

import java.util.*
import java.util.concurrent.BlockingQueue
import kotlin.math.absoluteValue

fun Iterable<Long>.product() = reduce { acc, item -> acc * item }
fun Iterable<Int>.product() = reduce { acc, item -> acc * item }

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

val IntArray.abs: Int get() = sumBy { it.absoluteValue }

fun lowestCommonMultiple(a: Long, b: Long): Long = (a * b) / greatestCommonDivisor(a, b)

/**
 * Use the euclidian algorithm to find GCD(A,B)
 * https://www.khanacademy.org/computing/computer-science/cryptography/modarithmetic/a/the-euclidean-algorithm
 *  - GCD(A,0) = A
 *  - GCD(0,B) = B
 *  - If A = BQ + R and B!=0 then GCD(A,B) = GCD(B,R) where Q is an integer, R is an integer between 0 and B-1
 */
//inline fun gcd = ::greatestCommonDivisor
tailrec fun greatestCommonDivisor(a: Long, b: Long): Long {
    return when {
        a == 0L -> b
        b == 0L -> a
        else -> {
            val first = maxOf(a, b)
            val second = minOf(a, b)
            greatestCommonDivisor(second, first % second)
        }
    }
}

fun IntArray.mapToInt(transform: (Int) -> Int): IntArray = IntArray(this.size) { transform(this[it]) }
fun IntArray.mapIndexedToInt(transform: (index: Int, value: Int) -> Int): IntArray =
    IntArray(this.size) { transform(it, this[it]) }
