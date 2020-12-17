package helper

class HyperspaceTrie<T>(lowerBounds: IntArray, upperBounds: IntArray) {

    private val rootNode = TrieNode.InnerNode<T>(lowerBounds, upperBounds, 0)

    operator fun set(location: IntArray, value: T) {
        rootNode[location] = value
    }

    operator fun get(index: IntArray): T? = rootNode[index]
    fun count(predicate: (T) -> Boolean): Int = rootNode.count(predicate)
    fun count(): Int = rootNode.count()
}

private sealed class TrieNode<T> {

    abstract fun count(predicate: (T) -> Boolean): Int
    abstract fun count(): Int

    class InnerNode<T>(val lowerBounds: IntArray, val upperBounds: IntArray, val dimension: Int) : TrieNode<T>() {
        private val lowerBound get() = lowerBounds[dimension]
        private val upperBound get() = upperBounds[dimension]
        private val nodes: Array<TrieNode<T>?> = Array(upperBound - lowerBound + 1) { null }

        operator fun set(location: IntArray, value: T) {
            val nodeIndex = location[dimension] - lowerBound

            if (dimension == location.lastIndex) {
                nodes[nodeIndex] = LeafNode(value)
            } else {
                val trieNode = nodes[nodeIndex]
                if (trieNode == null || trieNode !is InnerNode) {
                    val newNode = InnerNode<T>(lowerBounds, upperBounds, dimension + 1)
                    newNode[location] = value
                    nodes[nodeIndex] = newNode
                } else {
                    trieNode[location] = value
                }
            }
        }

        operator fun get(location: IntArray): T? {
            val nodeIndex = location[dimension] - lowerBound

            if (nodeIndex !in nodes.indices) {
                return null
            }

            return when (val nextNode = nodes[nodeIndex]) {
                null -> null
                is LeafNode -> nextNode.value
                is InnerNode -> nextNode[location]
            }
        }

        override fun count(predicate: (T) -> Boolean) = nodes.sumBy { it?.count(predicate) ?: 0 }
        override fun count(): Int = nodes.sumBy { it?.count() ?: 0 }
    }

    class LeafNode<T>(val value: T) : TrieNode<T>() {
        override fun count(predicate: (T) -> Boolean) = if (predicate(value)) 1 else 0
        override fun count(): Int = 1
    }
}