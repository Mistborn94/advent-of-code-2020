package day18

import java.util.*

fun solveA(lines: List<String>): Long {
    return lines.map { it.toCharArray().filter { char -> char != ' ' } }
        .map { buildPostfix(it, mapOf('*' to 1, '+' to 1)) }
        .map { evaluatePostfix(it) }
        .sum()
}

fun solveB(lines: List<String>): Long {
    return lines.map { it.toCharArray().filter { char -> char != ' ' } }
        .map { buildPostfix(it, mapOf('*' to 1, '+' to 2)) }
        .map { evaluatePostfix(it) }
        .sum()
}

/**
 * The magic here is called the Shunting Yard Algorithm
 * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 */
private fun buildPostfix(tokens: List<Char>, operatorPrecedence: Map<Char, Int>): Queue<Char> {
    val output: Queue<Char> = LinkedList()
    val operators: Stack<Char> = Stack()

    tokens.forEach { token ->
        when (token) {
            in '0'..'9' -> output.add(token)
            '(' -> operators.add(token)
            '+', '*' -> {
                while (operators.size > 0
                    && operators.peek() != '('
                    && operatorPrecedence[operators.peek()]!! >= operatorPrecedence[token]!!
                ) {
                    output.add(operators.pop())
                }
                operators.push(token)
            }
            ')' -> {
                while (operators.peek() != '(') {
                    output.add(operators.pop())
                }
                operators.pop()
            }
        }
    }

    while (!operators.isEmpty()) {
        output.add(operators.pop())
    }
    return output
}

private fun evaluatePostfix(postfix: Queue<Char>): Long {
    val numbers = Stack<Long>()

    postfix.forEach { token ->
        when (token) {
            in '0'..'9' -> numbers.push(token.toString().toLong())
            '+' -> numbers.push(numbers.pop() + numbers.pop())
            '*' -> numbers.push(numbers.pop() * numbers.pop())
        }
    }
    return numbers.pop()
}