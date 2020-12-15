package day15

fun solveA(input: String): Int {

    val history = mutableMapOf<Int, NumberHolder>()

    val numbers = input.split(",").map { it.toInt() }
    numbers.forEachIndexed { i, value ->
        val turn = i + 1
        history.getOrPut(value) { NumberHolder() }.next(turn)
    }

    var lastNumber = numbers.last()
    for (turn in (numbers.size + 1)..2020) {
        val historyItem = history[lastNumber]!!
        lastNumber = historyItem.calculate()
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

    fun calculate(): Int {
        return if (turn1 == null || turn2 == null) {
            0
        } else {
            turn2!! - turn1!!
        }
    }
}

fun solveB(input: String): Int {

    val history = mutableMapOf<Int, NumberHolder>()

    val numbers = input.split(",").map { it.toInt() }
    numbers.forEachIndexed { i, value ->
        val turn = i + 1
        history.getOrPut(value) { NumberHolder() }.next(turn)
    }

    var lastNumber = numbers.last()
    for (turn in (numbers.size + 1)..30_000_000) {
        val historyItem = history[lastNumber]!!
        lastNumber = historyItem.calculate()
        history.getOrPut(lastNumber) { NumberHolder() }.next(turn)
    }
    return lastNumber
}
