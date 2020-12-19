package day19

sealed class Rule {

    private var calculatedRegex: String? = null

    protected abstract fun calculateRegex(rules: List<Rule>): String

    open fun matches(rules: List<Rule>, message: String): Boolean {
        val regex = getRegex(rules).toRegex()
        return regex.matches(message)
    }

    fun getRegex(rules: List<Rule>): String {
        if (calculatedRegex == null) {
            calculatedRegex = calculateRegex(rules)
        }
        return calculatedRegex!!
    }

    class LetterRule(val letter: String) : Rule() {
        override fun calculateRegex(rules: List<Rule>): String = letter
    }

    open class SequenceRule(val numbers: List<Int>) : Rule() {
        override fun calculateRegex(rules: List<Rule>): String =
            numbers.joinToString(separator = "") { rules[it].getRegex(rules) }
    }

    class Rule0 : Rule() {
        override fun calculateRegex(rules: List<Rule>): String {
            val rule42Regex = rules[42].getRegex(rules)
            val rule31Regex = rules[31].getRegex(rules)

            return "((?:$rule42Regex)+)((?:$rule31Regex)+)"
        }

        override fun matches(rules: List<Rule>, message: String): Boolean {
            val matchResult = getRegex(rules).toRegex().matchEntire(message)

            val rule42Regex = rules[42].getRegex(rules)
            val rule31Regex = rules[31].getRegex(rules)

            if (matchResult != null) {
                val rule42Matches = rule42Regex.toRegex().findAll(matchResult.groups[1]!!.value)
                val rule31Matches = rule31Regex.toRegex().findAll(matchResult.groups[2]!!.value)
                return rule42Matches.count() > rule31Matches.count()
            }
            return false
        }

    }

    class OrRule(val sequence1: List<Int>, val sequence2: List<Int>) : Rule() {

        override fun calculateRegex(rules: List<Rule>): String {
            val part1 = sequence1.joinToString(separator = "") { rules[it].getRegex(rules) }
            val part2 = sequence2.joinToString(separator = "") { rules[it].getRegex(rules) }
            return "(?:$part1|$part2)"
        }
    }

}

fun solveA(text: String): Int {
    val (rulesText, messages) = text.replace("\r", "").split("\n\n")

    val rules: List<Rule> = rulesText.lines()
        .map { it.split(":") }
        .sortedBy { it[0].toInt() }
        .mapIndexed { i, (index, rulePart) ->
            assert(index.toInt() == i)
            buildRule(rulePart.trim())
        }

    return messages.split("\n").count { rules[0].matches(rules, it) }
}

private fun buildRule(rulePart: String): Rule {
    return when {
        '|' in rulePart -> {
            val (part1, part2) = rulePart.split("|")
            Rule.OrRule(part1.trim().split(" ").map { it.toInt() }, part2.trim().split(" ").map { it.toInt() })
        }
        "\"" in rulePart -> Rule.LetterRule(rulePart.trim(' ', '"'))
        else -> Rule.SequenceRule(rulePart.split(" ").map { it.toInt() })
    }
}


fun solveB(text: String): Int {
    val (rulesText, messages) = text.replace("\r", "").split("\n\n")


    val rules = rulesText.lines()
        .map { it.split(":") }
        .sortedBy { it[0].toInt() }
        .mapIndexed { i, (index, rulePart) ->
            assert(index.toInt() == i)
            buildRule(rulePart.trim())
        }.toMutableList()

    //New rules 8/11 gives rule 0 the following meaning:
    //Any number (at least two) of 42 occurrences followed by a lower number (at least one) of 31 occurrences
    rules[0] = Rule.Rule0()

    return messages.split("\n").count { rules[0].matches(rules, it) }
}
