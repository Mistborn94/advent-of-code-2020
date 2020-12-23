package day23

import java.util.*
import kotlin.collections.HashMap

fun solveA(labels: String, moveCount: Int = 100): String {

    val labelsList = labels.map { it.toString().toInt() }
    val cups = DistinctCircularList(labelsList)
    repeat(moveCount) {
        runIteration(cups, 9)
    }

    val node1 = cups.getNode(1)
    val stringBuilder = StringBuilder(labels.length)

    var current = node1.next
    while (current.value != node1.value) {
        stringBuilder.append(current.value)
        current = current.next
    }
    return stringBuilder.toString()
}

fun solveB(labels: String): Long {

    val maxCup = 1_000_000
    val iterations = 10_000_000

    val mutableList = LinkedList<Int>()
    mutableList.addAll(labels.map { it.toString().toInt() })
    mutableList.addAll(10..maxCup)
    val cups = DistinctCircularList(mutableList)

    repeat(iterations) {
        runIteration(cups, maxCup)
    }

    val node1 = cups.getNode(1)
    return node1.next.value.toLong() * node1.next.next.value.toLong()
}

private fun runIteration(cups: DistinctCircularList<Int>, maxCup: Int) {
    val currentCup = cups.head
    //pick up three cups
    val pickup1 = cups.remove(currentCup.next)
    val pickup2 = cups.remove(currentCup.next)
    val pickup3 = cups.remove(currentCup.next)
    //select destination cup
    val destinationCup = findDestination(currentCup.value, maxCup, pickup1, pickup2, pickup3)
    //re-add picked up cups
    cups.addAfter(destinationCup, listOf(pickup1, pickup2, pickup3))
    //select next current cup
    cups.shiftLeft()
}

private fun findDestination(
    currentCup: Int,
    maxCup: Int,
    pickup1: Int,
    pickup2: Int,
    pickup3: Int
): Int {
    var destinationCup = currentCup

    do {
        destinationCup -= 1
        if (destinationCup < 1) {
            destinationCup = maxCup
        }
    } while (destinationCup == pickup1 || destinationCup == pickup2 || destinationCup == pickup3)
    return destinationCup
}

class DistinctCircularList<T>(values: Collection<T>) {

    var head: Node<T>
    val nodes: MutableMap<T, Node<T>> = HashMap()

    init {
        if (values.isEmpty()) {
            throw IllegalArgumentException("Empty collections not supported")
        }

        val iterator = values.iterator()
        head = Node(iterator.next())
        nodes[head.value] = head

        var previous = head
        while (iterator.hasNext()) {
            val value = iterator.next()
            val newNode = Node(value)
            newNode.prev = previous
            previous.next = newNode
            previous = newNode
            nodes[value] = newNode
        }
        previous.next = head
        head.prev = previous
    }

    fun getNode(value: T) = nodes.getValue(value)

    fun remove(node: Node<T>): T {
        node.remove()
        return node.value
    }

    fun addAfter(value: T, elements: Collection<T>) = addAfter(nodes.getValue(value), elements)
    fun addAfter(startNode: Node<T>, elements: Collection<T>) {
        elements.iterator()
        val end = startNode.next
        var previous = startNode
        elements.forEach { value ->
            val newNode = nodes.getOrPut(value) { Node(value) }
            newNode.prev = previous
            previous.next = newNode
            previous = newNode
        }
        previous.next = end
        end.prev = previous
    }

    fun shiftLeft() {
        head = head.next
    }

    class Node<T>(val value: T) {
        lateinit var next: Node<T>
        lateinit var prev: Node<T>

        override fun toString(): String {
            return "Node(value=$value, prev=${prev.value}, next=${next.value})"
        }

        fun remove() {
            prev.next = next
            next.prev = prev
            //Because we cache the nodes, it is safer to clear out the next / prev references.
            next = this
            prev = this
        }
    }

}