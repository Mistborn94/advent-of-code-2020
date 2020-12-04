package day4

fun solveA(lines: List<String>): Int {
    val requiredFields = setOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    )

    var validCount = 0
    val currentFields = mutableSetOf<String>()
    lines.forEach {
        if (it.isBlank()) {
            if (currentFields.containsAll(requiredFields)) {
                validCount++
            }
            currentFields.clear()
        } else {
            currentFields.addAll(it.split(" ")
                .map { item -> item.substringBefore(":") })
        }

    }
    return validCount;
}


fun solveB(lines: List<String>): Int {
    val numerical = "\\d+".toRegex()
    val hclRegex = "#[0-9a-f]{6}".toRegex()
    val eyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val pidRegex = "\\d{9}".toRegex()
    val requiredFields = mapOf<String, (String) -> Boolean>(
        "byr" to { it.matches(numerical) && it.toInt() in 1920..2002 },
        "iyr" to { it.matches(numerical) && it.toInt() in 2010..2020 },
        "eyr" to { it.matches(numerical) && it.toInt() in 2020..2030 },
        "hgt" to {
            (it.endsWith("cm") && it.substringBefore("c").toInt() in 150..193)
                    || (it.endsWith("in") && it.substringBefore("i").toInt() in 59..76)
        },
        "hcl" to { it.matches(hclRegex) },
        "ecl" to { it in eyeColors },
        "pid" to { it.matches(pidRegex) }
    )


    var validCount = 0
    val currentFields = mutableSetOf<String>()
    lines.forEach {
        if (it.isBlank()) {
            if (currentFields.containsAll(requiredFields.keys)) {
                validCount++
            }
            currentFields.clear()
        } else {
            currentFields.addAll(
                it.split(" ")
                    .map { item -> item.split(":") }
                    .filter { (key, value) -> requiredFields[key]?.invoke(value) ?: true }
                    .map { (key, _) -> key }
            )
        }

    }
    return validCount;
}
