package day16

import helper.product

val pattern = """(\d+)-(\d+) or (\d+)-(\d+)""".toRegex()
fun solveA(lines: List<String>): Int {

    val fieldRules = extractFieldRules(lines)

    val nearbyTickets = lines.subList(lines.indexOf("nearby tickets:") + 1, lines.size)

    return nearbyTickets.asSequence()
        .flatMap { it.split(",").map(String::toInt) }
        .filter { value -> !fieldRules.values.any(value::let) }
        .sum()
}

private fun extractFieldRules(allLines: List<String>): Map<String, (Int) -> Boolean> =
    allLines
        .takeWhile(String::isNotBlank)
        .map {
            val key = it.substringBefore(":")
            val (a, b, c, d) = pattern.matchEntire(it.substringAfter(": "))!!.destructured

            key to { value: Int -> value in (a.toInt()..b.toInt()) || value in (c.toInt()..d.toInt()) }
        }.toMap()


fun solveB(lines: List<String>): Long {

    val fieldRules = extractFieldRules(lines)

    val yourTicket = lines[lines.indexOf("your ticket:") + 1].split(",").map(String::toInt)

    val possibleFieldNames = Array(yourTicket.size) { fieldRules.keys.toMutableSet() }
    val solvedFields = Array<String?>(yourTicket.size) { null }

    lines.subList(lines.indexOf("nearby tickets:") + 1, lines.size)
        .map { it.split(",").map(String::toInt) }
        //filter out completely invalid tickets
        .filter { numbers -> !containsInvalidFields(numbers, fieldRules) }
        .forEach { ticket ->
            ticket.forEachIndexed { i, value ->
                possibleFieldNames[i].removeIf { rule -> !fieldRules[rule]!!(value) }
            }
        }

    while (solvedFields.any { it == null }) {
        //Check for field names that are only possible in one position
        fieldRules.keys.filter { it !in solvedFields }.forEach { name ->
            val possibleIndices = possibleFieldNames.withIndex().filter { (_, it) -> it.contains(name) }

            if (possibleIndices.size == 1) {
                val (index, _) = possibleIndices.first()
                solvedFields[index] = name
                possibleFieldNames.forEach { it.remove(name) }
                possibleFieldNames[index].clear()
            }
        }

        //Check for positions that have only one allowed name
        possibleFieldNames.withIndex().filter { (_, names) -> names.size == 1 }.forEach { (index, names) ->
            val name = names.first()
            solvedFields[index] = name
            possibleFieldNames.forEach { it.remove(name) }
            possibleFieldNames[index].clear()
        }
    }

    return yourTicket.mapIndexed { i, value -> solvedFields[i]!! to value }
        .filter { (name, _) -> name.startsWith("departure") }
        .map { (_, value) -> value.toLong() }
        .product()
}

private fun containsInvalidFields(numbers: List<Int>, fieldRules: Map<String, (Int) -> Boolean>) =
    numbers.any { value -> !fieldRules.values.any(value::let) }