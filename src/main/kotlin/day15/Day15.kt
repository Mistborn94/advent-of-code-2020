package day15

fun solveA(input: String): Int {
    val startingNumbers = input.split(",").map { it.toInt() }
    return playTurns(startingNumbers, 2020)
}

fun solveB(input: String): Int {
    val startingNumbers = input.split(",").map { it.toInt() }
    return playTurns(startingNumbers, 30_000_000)
}

fun playTurns(startingNumbers: List<Int>, turns: Int): Int {
    val history = mutableMapOf<Int, Int>()

    (0 until startingNumbers.lastIndex).forEach {
        history[startingNumbers[it]] = it + 1
    }

    return (startingNumbers.size until turns).fold(startingNumbers.last()) { lastNumber, turn ->
        val nextNumber = history[lastNumber]?.let { turn - it } ?: 0
        history[lastNumber] = turn
        nextNumber
    }
}