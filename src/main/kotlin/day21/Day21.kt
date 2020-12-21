package day21

val regex = """([a-z ]+) \(contains ([a-z ,]+)\)""".toRegex()

fun solveA(lines: List<String>): Int {

    val allIngredients = mutableListOf<String>()
    //Map allergen to possible words
    val dictionary = mutableMapOf<String, MutableSet<String>>()

    lines.forEach { line ->
        val (ingredients, allergens) = buildRecipe(line)
        allIngredients.addAll(ingredients)

        allergens.forEach {
            dictionary.putIfAbsent(it, ingredients.toMutableSet())
            dictionary[it]!!.removeIf { ingredient -> ingredient !in ingredients }
        }
    }

    allIngredients.removeAll(dictionary.values.flatten())

    return allIngredients.size
}

private fun buildRecipe(
    it: String
): Pair<Set<String>, Set<String>> {
    val matchResult = regex.matchEntire(it)

    val ingredients = matchResult!!.groupValues[1].split(" ").map(String::trim).toSet()
    val allergens = matchResult.groupValues[2].split(",").map(String::trim).toSet()


    return Pair(ingredients, allergens)
}


fun solveB(lines: List<String>): String {

    //Map allergen to possible words
    val dictionary = mutableMapOf<String, MutableSet<String>>()

    lines.forEach { line ->
        val (ingredients, allergens) = buildRecipe(line)
        allergens.forEach {
            dictionary.putIfAbsent(it, ingredients.toMutableSet())
            dictionary[it]!!.removeIf { ingredient -> ingredient !in ingredients }
        }
    }

    //Map words to possible allergens
    val reversedDictionary = mutableMapOf<String, MutableSet<String>>()

    dictionary.entries.forEach { (allergen, possibleIngredients) ->
        possibleIngredients.forEach {
            reversedDictionary.putIfAbsent(it, mutableSetOf())
            reversedDictionary[it]!!.add(allergen)
        }
    }

    val solution = mutableMapOf<String, String>()
    while (reversedDictionary.any { (_, value) -> value.size == 1 }) {
        val (ingredient, possibleAllergens) = reversedDictionary.entries.first { (_, value) -> value.size == 1 }
        val allergen = possibleAllergens.first()
        solution[allergen] = ingredient
        reversedDictionary.remove(ingredient)
        reversedDictionary.values.forEach { it.remove(allergen) }
    }

    return solution.entries.sortedBy { it.key }.joinToString(separator = ",") { it.value }
}
