package day7

class Bag(private val colour: String) {

    val contains: MutableList<Pair<Bag, Int>> = mutableListOf()
    val containedIn: MutableList<Bag> = mutableListOf()

    fun containsCount(): Int = contains.sumBy { (bag, count) -> (bag.containsCount() + 1) * count }

    fun containedInNames(): Set<String> = containedIn.flatMap { it.containedInNames() + it.colour }.toSet()

    override fun toString(): String = "Bag(colour='$colour')"

}

class Graph {
    private val colourRegex = """^([A-Za-z ]+) bags contain (.*)\.$""".toRegex()
    private val containsRegex = """^(\d+) ([A-Za-z ]+) bags?$""".toRegex()

    private val bags: MutableMap<String, Bag> = mutableMapOf()

    operator fun get(bag: String) = bags[bag] ?: throw IllegalArgumentException("No item")

    fun addRule(rule: String) {
        val (colour, containsRule) = colourRegex.matchEntire(rule)!!.destructured

        val bag = getOrPut(colour)

        if (containsRule != "no other bags") {
            val parts = containsRule.split(",")
            parts.forEach {
                val (count, innerColour) = containsRegex.matchEntire(it.trim())!!.destructured
                val innerBag = getOrPut(innerColour)

                innerBag.containedIn.add(bag)
                bag.contains.add(Pair(innerBag, count.toInt()))
            }
        }
    }

    private fun getOrPut(colour: String) = bags.getOrPut(colour) { Bag(colour) }

}

fun buildGraph(lines: List<String>): Graph {
    return Graph().apply {
        lines.forEach(this::addRule)
    }
}

fun solveA(lines: List<String>): Int = buildGraph(lines)["shiny gold"].containedInNames().size
fun solveB(lines: List<String>): Int = buildGraph(lines)["shiny gold"].containsCount()

