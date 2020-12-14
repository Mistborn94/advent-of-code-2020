package day13

import helper.lowestCommonMultiple

fun solveA(lines: List<String>): Int {
    val start = lines[0].toInt()
    val bus = lines[1].split(",")
        .filter { it != "x" }
        .map { it.toInt() }
        .minByOrNull { remainingMinutes(it, start) } ?: throw IllegalStateException()

    return bus * remainingMinutes(bus, start)
}

private fun remainingMinutes(bus: Int, start: Int) = bus - (start % bus)

data class Bus(val id: Long, val offset: Int) {
    fun runsAt(timestamp: Long) = timestamp % id == 0L
}

data class LcmSequence(val start: Long, val increment: Long) {
    fun runningTimes() = generateSequence(start) { it + increment }
}

fun solveB(line: String, start: Long = 0L): Long {
    val busses = line
        .split(",")
        .mapIndexedNotNull { index, id -> if (id == "x") null else Bus(id.toLong(), index) }

    return busses.fold(LcmSequence(start, 1)) { combined, second ->
        val lcm = lowestCommonMultiple(combined.increment, second.id)

        val time = combined.runningTimes().first { time ->
            second.runsAt(time + second.offset)
        }
        LcmSequence(time, lcm)
    }.start
}
