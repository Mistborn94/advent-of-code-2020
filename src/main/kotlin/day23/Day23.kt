package day23

import helper.collections.IntCircularList
import java.util.*

fun solveA(labels: String, moveCount: Int = 100): String {

    val labelsList = labels.map { it.toString().toInt() }
    val cups = IntCircularList.ofValues(labelsList)
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
    val cups = IntCircularList.ofValues(mutableList)

    repeat(iterations) {
        runIteration(cups, maxCup)
    }

    val node1 = cups.getNode(1)
    return node1.next.value.toLong() * node1.next.next.value.toLong()
}

private fun runIteration(cups: IntCircularList, maxCup: Int) {
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

