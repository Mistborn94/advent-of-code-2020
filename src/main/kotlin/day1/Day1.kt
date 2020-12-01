package day1

/**
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 */
fun solveA(items: List<String>): Int {

    val groupBy = items.groupBy({ it[it.lastIndex].toString().toInt() }, { it.toInt() })

    for (first in 0..5) {
        val second = (10 - first) % 10

        val firstBucket = groupBy[first] ?: emptyList()
        val secondBucket = groupBy[second] ?: emptyList()

        for (i in firstBucket) {
            for (j in secondBucket) {
                if (i != j && i + j == 2020) {
                    return i * j
                }
            }
        }
    }

    throw IllegalStateException("No solution found")
}


/**
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 */
fun solveB(items: List<String>): Int {

    val groupBy = items.groupBy({ it[it.lastIndex].toString().toInt() }, { it.toInt() })

    for (first in 0..9) {
        val firstBucket = groupBy[first] ?: emptyList()

        for (second in 0..9) {
            val secondBucket = groupBy[second] ?: emptyList()

            val third = (10 - (first + second) % 10) % 10
            val thirdBucket = groupBy[third] ?: emptyList()

            for (i in firstBucket) {
                for (j in secondBucket) {
                    for (k in thirdBucket) {
                        if (i != j && i != k && k != j && i + j + k == 2020) {
                            return i * j * k
                        }
                    }
                }
            }

        }
    }

    throw IllegalStateException("No solution found")
}