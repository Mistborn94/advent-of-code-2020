package day6

fun solveA(text: String): Int {
    return text.replace("\r", "")
        .split("\n\n")
        .map {
            it.toCharArray()
                .filter { char -> char in 'a'..'z' }
                .toSet()
        }.sumBy { it.size }
}

fun solveB(text: String): Int {
    return text.replace("\r", "")
        .split("\n\n")
        .map { it.lines() }
        .map {
            it.fold(('a'..'z').toSet()) { acc, s -> s.toSet().intersect(acc) }
        }
        .sumBy { it.size }
}
