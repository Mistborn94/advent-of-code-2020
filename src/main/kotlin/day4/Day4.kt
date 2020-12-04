package day4

fun solveA(text: String): Int {
    val lines = text.replace("\r", "").split("\n\n")

    val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    return lines.count { passport ->
        passport.trim().split(" ", "\n")
            .map { item -> item.substringBefore(":") }
            .containsAll(requiredFields)
    }
}

fun solveB(text: String): Int {

    val lines = text.replace("\r", "").split("\n\n")

    val hclRegex = "#[0-9a-f]{6}".toRegex()
    val eyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val pidRegex = "\\d{9}".toRegex()

    val requiredFields = mapOf<String, (String) -> Boolean>(
        "byr" to { it.toInt() in 1920..2002 },
        "iyr" to { it.toInt() in 2010..2020 },
        "eyr" to { it.toInt() in 2020..2030 },
        "hgt" to {
            (it.endsWith("cm") && it.substringBefore("c").toInt() in 150..193)
                    || (it.endsWith("in") && it.substringBefore("i").toInt() in 59..76)
        },
        "hcl" to { it.matches(hclRegex) },
        "ecl" to { it in eyeColors },
        "pid" to { it.matches(pidRegex) }
    )

    return lines.count { passport ->
        passport.trim().split(" ", "\n")
            .map { item -> item.split(":") }
            .filter { (key, value) -> requiredFields[key]?.invoke(value) ?: true }
            .map { (key, _) -> key }
            .containsAll(requiredFields.keys)
    }
}
