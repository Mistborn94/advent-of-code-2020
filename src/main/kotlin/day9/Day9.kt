package day9

fun solveA(lines: List<String>, preambleSize: Int = 25): Long {

    return lines.map { it.toLong() }
        .windowed(preambleSize + 1, partialWindows = false)
        .first { cantMakeFromParts(it) }
        .last()
}

fun cantMakeFromParts(numbers: List<Long>): Boolean {
    val total = numbers.last()
    val parts = numbers.subList(0, numbers.lastIndex)

    return !parts.any {
        val remainder = total - it
        remainder != it && parts.contains(remainder)
    }
}

fun solveB(lines: List<String>, solutionA: Long = solveA(lines)): Long {
    val numbers = lines.map { it.toLong() }
    var first = 0
    var last = 1

    var runningSum = numbers[0] + numbers[1]

    while (runningSum != solutionA) {
        if (runningSum < solutionA) {
            last += 1
            runningSum += numbers[last]
        } else if (runningSum > solutionA) {
            runningSum -= numbers[first]
            first += 1
        }
    }

    val sequence = numbers.subList(first, last + 1)
    return sequence.minOrNull()!! + sequence.maxOrNull()!!
}

