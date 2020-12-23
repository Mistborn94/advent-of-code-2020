package helper.collections

/**
 * Circular list containing unique integer values
 */
class IntCircularList(var head: IntNode, private val nodes: Array<IntNode?>) {

    fun getNode(value: Int) = nodes.getValue(value)

    fun remove(node: IntNode): Int {
        node.remove()
        return node.value
    }

    fun addAfter(value: Int, elements: Collection<Int>) = addAfter(nodes.getValue(value), elements)
    fun addAfter(startNode: IntNode, elements: Collection<Int>) {
        elements.iterator()
        val end = startNode.next
        var previous = startNode
        elements.forEach { value ->
            val newNode = nodes.getOrPut(value) { IntNode(value) }
            newNode.prev = previous
            previous.next = newNode
            previous = newNode
        }
        previous.next = end
        end.prev = previous
    }

    fun shiftLeft() {
        head = head.next
    }

    class IntNode(val value: Int) {
        lateinit var next: IntNode
        lateinit var prev: IntNode

        override fun toString(): String {
            return "Node(value=$value, prev=${prev.value}, next=${next.value})"
        }

        fun remove() {
            prev.next = next
            next.prev = prev
            //Because we cache the nodes, it is safer to clear out the next / prev references.
            next = this
            prev = this
        }
    }

    private fun <T> Array<T?>.getValue(value: Int) = this[value] ?: throw NoSuchElementException()
    private fun <T> Array<T?>.getOrPut(key: Int, defaultValue: () -> T): T {
        val currentValue = this[key]

        if (currentValue == null) {
            val newValue = defaultValue()
            this[key] = newValue
            return newValue
        } else {
            return currentValue
        }
    }

    companion object {
        fun ofValues(values: Collection<Int>, max: Int = values.maxOrNull()!!): IntCircularList {
            if (values.isEmpty()) {
                throw IllegalArgumentException("Empty collections not supported")
            }
            val nodes = Array<IntNode?>(max + 1) { null }
            val iterator = values.iterator()
            val head = IntNode(iterator.next())
            nodes[head.value] = head
            var previous = head
            while (iterator.hasNext()) {
                val value = iterator.next()
                val newNode = IntNode(value)
                newNode.prev = previous
                previous.next = newNode
                previous = newNode
                nodes[value] = newNode
            }
            previous.next = head
            head.prev = previous
            return IntCircularList(head, nodes)
        }
    }

}

