package helper

class HyperspacePoint private constructor(private val parts: IntArray) {
    val size = parts.size

    val neighbours: List<HyperspacePoint>
        get() = neighbourOffsets(size)
            .filter { it.abs != 0 }
            .map { of(*it) }
            .map { it + this }
            .toList()

    operator fun get(i: Int) = parts[i]

    operator fun plus(other: HyperspacePoint) =
        HyperspacePoint(IntArray(minOf(size, other.size)) { parts[it] + other.parts[it] })

    operator fun minus(other: HyperspacePoint) =
        HyperspacePoint(IntArray(minOf(size, other.size)) { parts[it] - other.parts[it] })

    private fun neighbourOffsets(dimension: Int): Iterable<IntArray> {
        return if (dimension == 1) {
            listOf(IntArray(1) { -1 }, IntArray(1) { 0 }, IntArray(1) { 1 })
        } else {
            neighbourOffsets(dimension - 1).flatMap { sequenceOf(it + -1, it + 0, it + 1) }
        }
    }

    override fun equals(other: Any?) = other is HyperspacePoint && parts.contentEquals(other.parts)
    override fun hashCode(): Int = parts.contentHashCode()
    override fun toString(): String = parts.toString()

    companion object {
        fun of(vararg parts: Int) = HyperspacePoint(parts)
    }
}