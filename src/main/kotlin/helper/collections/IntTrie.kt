package helper.collections

class IntTrie(lowerBounds: IntArray, upperBounds: IntArray) {

    private val rootNode = TrieNode.InnerNode(lowerBounds, upperBounds, 0)
    fun add(location: IntArray) {
        rootNode.add(location)
    }

    operator fun contains(index: IntArray) = rootNode.contains(index)

    fun count(): Int = rootNode.count()
}

private sealed class TrieNode {

    abstract fun count(): Int

    class InnerNode(val lowerBounds: IntArray, val upperBounds: IntArray, val dimension: Int) : TrieNode() {
        private val lowerBound get() = lowerBounds[dimension]
        private val upperBound get() = upperBounds[dimension]
        private val nodes: Array<TrieNode?> = Array(upperBound - lowerBound + 1) { null }

        fun add(location: IntArray) {
            val nodeIndex = location[dimension] - lowerBound

            if (dimension == location.lastIndex) {
                nodes[nodeIndex] = LeafNode
            } else {
                val trieNode = nodes[nodeIndex]
                if (trieNode == null || trieNode !is InnerNode) {
                    val newNode = InnerNode(lowerBounds, upperBounds, dimension + 1)
                    newNode.add(location)
                    nodes[nodeIndex] = newNode
                } else {
                    trieNode.add(location)
                }
            }
        }

        fun contains(location: IntArray): Boolean {
            val nodeIndex = location[dimension] - lowerBound

            if (nodeIndex !in nodes.indices) {
                return false
            }

            return when (val nextNode = nodes[nodeIndex]) {
                null -> false
                is LeafNode -> true
                is InnerNode -> nextNode.contains(location)
            }
        }

        override fun count(): Int = nodes.sumBy { it?.count() ?: 0 }
    }

    object LeafNode : TrieNode() {
        override fun count(): Int = 1
    }
}