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

