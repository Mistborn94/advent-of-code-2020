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
    val history = mutableMapOf<Int, NumberHolder>()

    startingNumbers.forEachIndexed { i, value ->
        history.getOrPut(value) { NumberHolder() }.next(i + 1)
    }

    var lastNumber = startingNumbers.last()
    for (turn in (startingNumbers.size + 1)..turns) {
        lastNumber = history[lastNumber]!!.calculate()
        history.getOrPut(lastNumber) { NumberHolder() }.next(turn)
    }

    return lastNumber
}

class NumberHolder {
    private var turn1: Int? = null
    private var turn2: Int? = null

    fun next(number: Int) {
        turn1 = turn2
        turn2 = number
    }

    fun calculate(): Int = if (turn1 == null) 0 else turn2!! - turn1!!
}

