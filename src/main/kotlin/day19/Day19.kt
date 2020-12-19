package day19

sealed class Rule {

    abstract fun toRegex(rules: List<Rule>): String

    class LetterRule(val letter: String) : Rule() {
        override fun toRegex(rules: List<Rule>): String = letter
    }

    open class SequenceRule(val numbers: List<Int>) : Rule() {
        override fun toRegex(rules: List<Rule>): String =
            numbers.joinToString("") { rules[it].toRegex(rules) }
    }

    class OrRule(val sequence1: List<Int>, val sequence2: List<Int>) : Rule() {
        override fun toRegex(rules: List<Rule>): String {
            val part1 = sequence1.joinToString("") { rules[it].toRegex(rules) }
            val part2 = sequence2.joinToString("") { rules[it].toRegex(rules) }
            return "(?:$part1|$part2)"
        }
    }

    object Rule0B : Rule() {
        override fun toRegex(rules: List<Rule>): String {
            val rule42Regex = rules[42].toRegex(rules)
            val rule31Regex = rules[31].toRegex(rules)

            return "((?:$rule42Regex)+)((?:$rule31Regex)+)"
        }

        fun matches(rules: List<Rule>, message: String): Boolean {
            val matchResult = toRegex(rules).toRegex().matchEntire(message)

            val rule42Regex = rules[42].toRegex(rules)
            val rule31Regex = rules[31].toRegex(rules)

            if (matchResult != null) {
                val rule42Matches = rule42Regex.toRegex().findAll(matchResult.groups[1]!!.value)
                val rule31Matches = rule31Regex.toRegex().findAll(matchResult.groups[2]!!.value)
                return rule42Matches.count() > rule31Matches.count()
            }
            return false
        }

    }
}

fun solveA(text: String): Int {
    val (rulesText, messages) = text.replace("\r", "").split("\n\n")
    val rules: List<Rule> = buildRules(rulesText)
    val rule0 = rules[0].toRegex(rules).toRegex()
    return messages.split("\n").count { rule0.matches(it) }
}

fun solveB(text: String): Int {
    val (rulesText, messages) = text.replace("\r", "").split("\n\n")
    val rules = buildRules(rulesText)

    //New rules 8/11 gives rule 0 the following meaning:
    //Any number (at least two) of 42 occurrences followed by a lower number (at least one) of 31 occurrences
    return messages.split("\n").count { Rule.Rule0B.matches(rules, it) }
}

private fun buildRules(rulesText: String) = rulesText.lines()
    .map { it.split(":") }
    .sortedBy { it[0].toInt() }
    .mapIndexed { i, (index, rulePart) ->
        assert(index.toInt() == i)
        buildRule(rulePart.trim())
    }

private fun buildRule(rulePart: String): Rule {
    return when {
        '|' in rulePart -> {
            val (part1, part2) = rulePart.split("|")
            Rule.OrRule(part1.trim().split(" ").map { it.toInt() }, part2.trim().split(" ").map { it.toInt() })
        }
        '"' in rulePart -> Rule.LetterRule(rulePart.trim(' ', '"'))
        else -> Rule.SequenceRule(rulePart.split(" ").map { it.toInt() })
    }
}
