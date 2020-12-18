package day18

import java.util.*

fun solveA(lines: List<String>): Long {

    return lines.map { evaluateA(it.toCharArray().filter { char -> char != ' ' }) }.sum()
}

fun evaluateA(parts: List<Char>): Long {
    val numbers: Stack<Long> = Stack()

    var index = 0
    while (index < parts.size) {
        val first = parts[index]
        when {
            first == '(' -> {
                val (nextIndex, nextValue) = nextExpressionA(parts, index)
                numbers.push(nextValue)
                index = nextIndex
            }
            first.isDigit() -> {
                numbers.push(first.toString().toLong())
                index += 1
            }
            first == '+' -> {
                val (nextIndex, nextValue) = nextExpressionA(parts, index + 1)
                numbers.push(numbers.pop() + nextValue)
                index = nextIndex
            }
            first == '*' -> {
                val (nextIndex, nextValue) = nextExpressionA(parts, index + 1)
                numbers.push(numbers.pop() * nextValue)
                index = nextIndex
            }
        }
    }
    assert(numbers.size == 1)
    return numbers.pop()
}

fun nextExpressionA(parts: List<Char>, startingIndex: Int): Pair<Int, Long> {
    val first = parts[startingIndex]

    when {
        first.isDigit() -> return Pair(startingIndex + 1, first.toString().toLong())
        first == '(' -> {
            var bracketCount = 1
            var lastIndex = startingIndex + 1
            while (bracketCount != 0) {
                when (parts[lastIndex]) {
                    '(' -> bracketCount++
                    ')' -> bracketCount--
                }
                lastIndex++
            }
            val subList = parts.subList(startingIndex + 1, lastIndex - 1)
            val evaluate = evaluateA(subList)
            return Pair(lastIndex, evaluate)
        }
        else -> throw IllegalArgumentException("Unexpected Character $first")
    }
}

fun solveB(lines: List<String>): Long {
    return lines.map { evaluateB(it.toCharArray().filter { char -> char != ' ' }) }.sum()
}

fun evaluateB(parts: List<Char>): Long {
    val numbers: Stack<Long> = Stack()

    var index = 0
    while (index < parts.size) {
        val first = parts[index]
        when {
            first == '(' -> {
                val (nextIndex, nextValue) = nextExpressionB(parts, index)
                numbers.push(nextValue)
                index = nextIndex
            }
            first.isDigit() -> {
                numbers.push(first.toString().toLong())
                index += 1
            }
            first == '+' -> {
                val (nextIndex, nextValue) = nextExpressionB(parts, index + 1)
                numbers.push(numbers.pop() + nextValue)
                index = nextIndex
            }
            first == '*' -> {
                val nextValue = evaluateB(parts.subList(index + 1, parts.size))
                numbers.push(numbers.pop() * nextValue)
                index = parts.size
            }
        }
    }
    assert(numbers.size == 1)
    return numbers.pop()
}

fun nextExpressionB(parts: List<Char>, startingIndex: Int): Pair<Int, Long> {
    val first = parts[startingIndex]

    when {
        first.isDigit() -> return Pair(startingIndex + 1, first.toString().toLong())
        first == '(' -> {
            var bracketCount = 1
            var lastIndex = startingIndex + 1
            while (bracketCount != 0) {
                when (parts[lastIndex]) {
                    '(' -> bracketCount++
                    ')' -> bracketCount--
                }
                lastIndex++
            }
            val subList = parts.subList(startingIndex + 1, lastIndex - 1)
            val evaluate = evaluateB(subList)
            return Pair(lastIndex, evaluate)
        }
        else -> throw IllegalArgumentException("Unexpected Character $first")
    }
}
