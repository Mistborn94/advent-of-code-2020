package day7

class Bag(val name: String) {

    val contains: MutableList<Pair<Bag, Int>> = mutableListOf()
    val containedIn: MutableList<Bag> = mutableListOf()

    fun containsCount(): Int = contains.sumBy { (bag, count) ->
        (bag.containsCount() + 1) * count
    }

    override fun toString(): String {
        return "Bag(name='$name')"
    }

}

class Graph {
    val colourRegex = """^([A-Za-z ]+) bags contain (.*)\.$""".toRegex()
    val containsRegex = """^(\d+) ([A-Za-z ]+) bags?$""".toRegex()

    private val bags: MutableMap<String, Bag> = mutableMapOf()

    operator fun get(bag: String) = bags[bag]

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

val shinyGold = "shiny gold"
fun solveA(lines: List<String>): Int {
    val graph = Graph()
    lines.forEach(graph::addRule)

    val seenBags = mutableSetOf<String>()
    var currentBag = graph[shinyGold]!!
    val toSeeBags = currentBag.containedIn.toMutableSet()

    while (toSeeBags.isNotEmpty()) {
        currentBag = toSeeBags.pop()
        seenBags.add(currentBag.name)
        toSeeBags.addAll(currentBag.containedIn.filter { it.name !in seenBags })
    }

    return seenBags.size
}

private fun MutableSet<Bag>.pop(): Bag {
    val first = first()
    remove(first)
    return first
}


fun solveB(lines: List<String>): Int {
    val graph = Graph()
    lines.forEach(graph::addRule)

    return graph[shinyGold]!!.containsCount()
}
