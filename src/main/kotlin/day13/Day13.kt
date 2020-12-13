package day13

fun solveA(lines: List<String>): Int {
    val start = lines[0].toInt()
    val bus = lines[1].split(",")
        .filter { it != "x" }
        .map { it.toInt() }
        .minByOrNull { remainingMinutes(it, start) } ?: throw IllegalStateException()

    return bus * remainingMinutes(bus, start)
}

private fun remainingMinutes(bus: Int, start: Int) = bus - (start % bus)

data class RunningBus(val id: Int, val offset: Int) {

    fun runsAt(timestamp: Long) = timestamp % id == 0L
    val runningTimes = generateSequence(id.toLong()) { it + id }
}

fun solveB(lines: List<String>): Long {
    val busses = lines[1]
        .split(",")
        .mapIndexedNotNull { index, line -> if (line == "x") null else RunningBus(line.toInt(), index) }

    val maxBus = busses.maxByOrNull { it.id }!!
    val remainingBusses = busses.filter { it != maxBus }

    return maxBus.runningTimes.first { time ->
        val firstTime = time - maxBus.offset
        remainingBusses.all { it.runsAt(firstTime + it.offset) }
    } - maxBus.offset
}
