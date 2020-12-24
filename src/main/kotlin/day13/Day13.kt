package day13

import helper.lowestCommonMultiple
import helper.product

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

/**
 * Solve using Chinese Remainder Theorem
 *
 * x % num[0]    =  rem[0],
 * x % num[1]    =  rem[1],
 * .......................
 * x % num[k-1]  =  rem[k-1]
 *
 * https://www.geeksforgeeks.org/chinese-remainder-theorem-set-1-introduction/
 * https://www.geeksforgeeks.org/chinese-remainder-theorem-set-2-implementation/
 *
 * Terms:
 * Coprime:
 * -> Two numbers are coprime if they do not have any common divisors except 1
 * The input set is pairwise coprime:
 * -> All numbers in the set are coprimes of all other numbers in the set
 * Modular Multiplicative Inverse
 * -> The Modular Multiplicative Inverse of a with respect to / under m is a number x in {0,1,2....m-1} where ax % m = 1
 * -> a = 3, m = 11 then x = 4: (4*3) mod 11 = 1
 *
 */
fun solveBAlternate(line: String): Long {
    val busses = line
        .split(",")
        .mapIndexedNotNull { index, id -> if (id == "x") null else Bus(id.toLong(), index) }

    val numbers = busses.map { it.id }
    val remainders = busses.map { it.id - it.offset.toLong() }

    return solveRemainders(numbers, remainders)
}

/**
 * Find x where
 *  x % moduli[0] = remainders[0]
 */
fun solveRemainders(
    moduli: List<Long>,
    remainders: List<Long>
): Long {
    val indices = moduli.indices

    val moduloProduct = moduli.product()
    val partialProducts = moduli.map { moduloProduct / it }
    val inverse = indices.map { mmi(partialProducts[it], moduli[it]) }

    return indices.map { remainders[it] * partialProducts[it] * inverse[it] }.sum() % moduloProduct
}

/**
 * Calculate the Modular Multiplicative Inverse [x] of [a] under [m]
 * ax mod m = 1 where x in {0,1,2....m-1}
 */
fun mmi(a: Long, m: Long): Long {
    val (g, x, y) = extendedGcd(a, m)
    //Make it positive. Just in case.
    val positiveX = makePositive(m, x)

    assert(g == 1L) { "$a and $m is not coprime" }
    assert(a * positiveX % m == 1L) { "$a * $positiveX % $m != 1" }
    assert(positiveX in 0 until m) { "x not in range 0..${m - 1}" }

    return positiveX
}

private fun makePositive(m: Long, x: Long) = (m + x) % m

/**
 * https://cp-algorithms.com/algebra/extended-euclid-algorithm.html
 * Calculate the gcd and x and y where
 *  ax + by = gcd(a,b)
 */
fun extendedGcd(a: Long, b: Long): ExtendedGcd {
    return when {
        //a = 0, so by = b
        a == 0L -> ExtendedGcd(b, 0, 1)
        //b = 0 so ax = a
        b == 0L -> ExtendedGcd(a, 1, 0)
        else -> {
            val (g, x1, y1) = extendedGcd(b, a % b)
            //This result means: b⋅x1 + (a % b)⋅y1 = g
            //And: a % b = a − ⌊a/b⌋⋅b

            //With substitution:
            //g = b⋅x1 + (a − ⌊a/b⌋⋅b)⋅y1

            //And expanded:
            //g = b⋅x1 + y1⋅a − ⌊a/b⌋⋅b⋅y1

            //Now rearrange to the format g = ax + by
            //g = a⋅y1 + b(x1 - y1⋅⌊a/b⌋)
            //x = y1
            val x = y1
            //y = x1 - y1⋅⌊a/b⌋
            val y = x1 - y1 * (a / b)
            ExtendedGcd(g, x, y)
        }
    }
}

//ax + by = gcd(a,b)
data class ExtendedGcd(val g: Long, val x: Long, val y: Long)