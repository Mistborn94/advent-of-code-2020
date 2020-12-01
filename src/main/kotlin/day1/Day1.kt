package day1

/**
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 */
fun solveA(items: List<String>): Int {

    val numbers = items.map { it.toInt() }.toSet()

    return findParts(numbers, 2020) ?: throw IllegalStateException("No solution found")
}

private fun findParts(numbers: Set<Int>, requiredSum: Int): Int? {

    for (first in numbers) {
        val second = requiredSum - first
        if (second in numbers) {
            return first * second
        }
    }
    return null
}


/**
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 */
fun solveB(items: List<String>): Int {

    val numbers = items.map { it.toInt() }.toMutableSet()

    for (first in numbers) {
        val parts = findParts(numbers, 2020 - first)

        if (parts != null) {
            return parts * first
        }
    }

    throw IllegalStateException("No solution found")
}