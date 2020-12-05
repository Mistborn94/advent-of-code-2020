package day5

fun solveA(text: String): Int? {
    return getSeats(text)
        .maxOrNull()
}

private fun getSeats(text: String) = text.replace("[BR]".toRegex(), "1")
    .replace("[FL]".toRegex(), "0")
    .split("\n")
    .map { calSeatId(it) }

fun calSeatId(line: String): Int {
    val row = line.substring(0 until 7).toInt(2)
    val column = line.substring(7 until 10).toInt(2)
    return row * 8 + column
}

fun solveB(text: String): Int {
    val window = getSeats(text)
        .sorted()
        .windowed(2, 1)
        .first { (a, b) -> a + 1 != b }
    return window[0] + 1
}

