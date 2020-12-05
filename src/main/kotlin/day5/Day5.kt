package day5

fun solveA(text: String) = getSeats(text).maxOrNull()

private fun getSeats(text: String) = text.replace("[BR]".toRegex(), "1").replace("[FL]".toRegex(), "0")
    .split("\n")
    .map { it.toInt(2) }

fun solveB(text: String): Int {
    val window = getSeats(text)
        .sorted()
        .windowed(2, 1)
        .first { (a, b) -> a + 1 != b }
    return window[0] + 1
}

