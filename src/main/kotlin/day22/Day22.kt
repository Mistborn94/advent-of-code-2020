package day22

import java.util.*

fun solveA(text: String): Int {
    val (part1, part2) = text.replace("\r", "").split("\n\n")

    val player1Cards: Queue<Int> = LinkedList(part1.trim().lines().drop(1).map { it.toInt() })
    val player2Cards: Queue<Int> = LinkedList(part2.trim().lines().drop(1).map { it.toInt() })

    while (player1Cards.isNotEmpty() && player2Cards.isNotEmpty()) {
        val player1 = player1Cards.poll()
        val player2 = player2Cards.poll()

        if (player1 > player2) {
            player1Cards.add(player1)
            player1Cards.add(player2)
        } else {
            player2Cards.add(player2)
            player2Cards.add(player1)
        }
    }

    val winningCards = if (player1Cards.isNotEmpty()) player1Cards else player2Cards

    return winningCards.mapIndexed { i, value -> (winningCards.size - i) * value }.sum()
}

fun solveB(text: String): Int {
    val (part1, part2) = text.replace("\r", "").split("\n\n")

    val player1Cards = LinkedList(part1.trim().lines().drop(1).map { it.toInt() })
    val player2Cards = LinkedList(part2.trim().lines().drop(1).map { it.toInt() })

    val winner = recursiveCombat(player1Cards, player2Cards)

    val winningCards = if (winner == 1) player1Cards else player2Cards

    return winningCards.mapIndexed { i, value -> (winningCards.size - i) * value }.sum()
}

private fun recursiveCombat(player1Cards: LinkedList<Int>, player2Cards: LinkedList<Int>): Int {

    val seenConfigurations = mutableSetOf<Pair<List<Int>, List<Int>>>()

    while (player1Cards.isNotEmpty() && player2Cards.isNotEmpty()) {
        val roundConfiguration = Pair(player1Cards.toMutableList(), player2Cards.toMutableList())

        if (roundConfiguration in seenConfigurations) {
            return 1
        }

        seenConfigurations.add(roundConfiguration)

        val player1 = player1Cards.poll()
        val player2 = player2Cards.poll()

        val winner = if (player1 <= player1Cards.size && player2 <= player2Cards.size) {
            recursiveCombat(LinkedList(player1Cards.subList(0, player1)), LinkedList(player2Cards.subList(0, player2)))
        } else if (player1 > player2) {
            1
        } else {
            2
        }

        if (winner == 1) {
            player1Cards.add(player1)
            player1Cards.add(player2)
        } else {
            player2Cards.add(player2)
            player2Cards.add(player1)
        }
    }

    return if (player1Cards.isNotEmpty()) 1 else 2
}