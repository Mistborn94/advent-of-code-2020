package day10

fun solveA(lines: List<String>): Int {
    val all = getFullList(lines)

    val diffs = getDiffs(all)

    val mapValues = diffs
        .groupBy { it }
        .mapValues { (_, value) -> value.size }

    return mapValues[1]!! * mapValues[3]!!
}

private fun getDiffs(all: List<Int>) = all.zipWithNext()
    .map { (a, b) -> b - a }

private fun getFullList(lines: List<String>): List<Int> {
    val sorted = lines.map { it.toInt() }.sorted()
    return listOf(0) + sorted + (sorted.last() + 3)
}


fun solveB(lines: List<String>): Long {
    val fullList = getFullList(lines)
    val diffs = getDiffs(fullList)

    var total = 1L
    var currentIndex = 0
    while (currentIndex != diffs.lastIndex) {
        val nextCheckpoint = nextCheckpoint(currentIndex, diffs)
        if (nextCheckpoint - currentIndex > 1) {
            total *= count(0, fullList.subList(currentIndex, nextCheckpoint + 1))
        }
        currentIndex = nextCheckpoint
    }
    return total
}

fun nextCheckpoint(currentIndex: Int, diffs: List<Int>): Int {
    val offset = currentIndex + 1
    return diffs.subList(offset, diffs.size)
        .indexOf(3) + offset
}

fun count(currentIndex: Int, sortedList: List<Int>): Long {
    if (currentIndex == sortedList.lastIndex) {
        return 1
    }

    val currentValue = sortedList[currentIndex]

    return sortedList.subList(currentIndex + 1, sortedList.size)
        .takeWhile { it - currentValue <= 3 }
        .mapIndexed { index, _ ->
            count(currentIndex + index + 1, sortedList)
        }.sum()
}
