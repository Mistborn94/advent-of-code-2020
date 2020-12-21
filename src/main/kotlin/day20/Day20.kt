package day20

import helper.Point
import helper.product

val tileIdRegex = """Tile (\d+):""".toRegex()
val monsterPattern =
    """|                  # 
       |#    ##    ##    ###
       | #  #  #  #  #  #   """.trimMargin()

val monsterIndices = monsterPattern.lines().flatMapIndexed { y, line ->
    line.withIndex().filter { (_, value) -> value == '#' }.map { (x, _) -> Point(x, y) }
}

class TileData(val id: Long, val data: List<String>) {

    val top = data.first()
    val bottom = data.last()
    val left = data.joinToString("") { it.first().toString() }
    val right = data.joinToString("") { it.last().toString() }

    private val borders = setOf(top, bottom, left, right)

    fun isCorner(allTiles: List<TileData>) =
        borders.count { border -> allTiles.any { it != this && it.hasBorder(border) } } == 2

    private fun hasBorder(border: String) = border in borders || border.reversed() in borders

    fun seaMonsterCount(): Int = data.mapIndexed { y, line -> line.indices.count { x -> isMonster(Point(x, y)) } }.sum()
    fun seaRoughness() = data.sumBy { it.count { char -> char == '#' } } - seaMonsterCount() * monsterIndices.size
    private fun isMonster(topLeft: Point): Boolean = monsterIndices.map { it + topLeft }
        .all { (x, y) -> y in data.indices && x in data[y].indices && data[y][x] == '#' }

    private fun flip() = TileData(id, data.reversed())

    private fun rotate(): TileData {
        val newData = data.indices.map { y -> data.indices.map { x -> data[x][data.size - 1 - y] }.joinToString("") }
        return TileData(id, newData)
    }

    fun orientations(): List<TileData> {
        val rot1 = this.rotate()
        val rot2 = rot1.rotate()
        val rot3 = rot2.rotate()

        return listOf(this, rot1, rot2, rot3) + listOf(this.flip(), rot1.flip(), rot2.flip(), rot3.flip())
    }

    fun trimBorders(): List<String> = data.subList(1, data.lastIndex).map { it.substring(1, it.lastIndex) }

    override fun toString() = "TileData(id=$id)"
}

class ImageNode(val tileData: TileData) {
    val id = tileData.id
    var left: ImageNode? = null
    var right: ImageNode? = null
    var top: ImageNode? = null
    var bottom: ImageNode? = null

    fun linkTop(tiles: List<TileData>): ImageNode? {
        val link = tiles.firstOrNull { tileData.top == it.bottom }
        return link?.let {
            val node = ImageNode(it)
            linkTop(node)
            node
        }
    }

    fun linkTop(node: ImageNode?) {
        assert(node == null || node.tileData.bottom == tileData.top)
        this.top = node
        node?.bottom = this
    }

    fun linkBottom(tiles: List<TileData>): ImageNode? {
        val link = tiles.firstOrNull { tileData.bottom == it.top }
        return link?.let {
            val node = ImageNode(it)
            linkBottom(node)
            node
        }
    }

    fun linkBottom(node: ImageNode?) {
        assert(node == null || node.tileData.top == tileData.bottom)
        this.bottom = node
        node?.top = this
    }

    fun linkLeft(tiles: List<TileData>): ImageNode? {
        val link = tiles.firstOrNull { tileData.left == it.right }
        return link?.let {
            val node = ImageNode(it)
            left = node
            node.right = this
            node
        }
    }

    fun linkRight(tiles: List<TileData>): ImageNode? {
        val link = tiles.firstOrNull { tileData.right == it.left }
        return link?.let {
            val node = ImageNode(it)
            right = node
            node.left = this
            node
        }
    }

    fun combine(): List<String> {
        val newData = tileData.trimBorders()
        val rowData = if (right != null) {
            newData.zip(right!!.combine()) { a, b -> a + b }
        } else {
            newData
        }

        return if (left == null && bottom != null) {
            rowData + bottom!!.combine()
        } else {
            rowData
        }
    }

    override fun toString(): String {
        val right = if (right != null) "->$right" else ""
        val bottom = if (left == null && bottom != null) "\n$bottom" else ""
        return "[$id]$right$bottom"
    }
}

fun solveA(text: String): Long {
    val tiles = buildTileData(text)

    val corners = tiles.filter { it.isCorner(tiles) }
    return corners.map { it.id }.product()
}

fun solveB(text: String): Int {
    val tiles = buildTileData(text)
    val topLeft = buildGrid(tiles)

    val final = TileData(0, topLeft.combine()).orientations().first { it.seaMonsterCount() > 0 }

    return final.seaRoughness()
}

private fun buildGrid(tiles: List<TileData>): ImageNode {
    var start = ImageNode(tiles.first())
    val remaining = tiles.drop(1).flatMap { it.orientations() }.toMutableList()

    buildChainRight(start, remaining)
    start = buildChainLeft(start, remaining)

    var current = start
    while (current.linkBottom(remaining) != null) {
        current = current.bottom!!
        remaining.removeIf { it.id == current.id }
        buildChainRight(current, remaining)
    }

    current = start
    while (current.linkTop(remaining) != null) {
        current = current.top!!
        remaining.removeIf { it.id == current.id }
        buildChainRight(current, remaining)
    }
    return current
}

fun buildChainLeft(start: ImageNode, remaining: MutableList<TileData>): ImageNode {
    var above = start.top
    var below = start.bottom

    var current = start
    while (current.linkLeft(remaining) != null) {
        current = current.left!!
        remaining.removeIf { it.id == current.id }
        above = above?.left
        below = below?.left
        current.linkTop(above)
        current.linkBottom(below)
    }
    return current
}

fun buildChainRight(start: ImageNode, remaining: MutableList<TileData>) {
    var above = start.top
    var below = start.bottom

    var current = start
    while (current.linkRight(remaining) != null) {
        current = current.right!!
        remaining.removeIf { it.id == current.id }
        above = above?.right
        below = below?.right
        current.linkTop(above)
        current.linkBottom(below)
    }
}

private fun buildTileData(text: String) = text.replace("\r", "").trim().split("\n\n").map { section ->
    val lines = section.lines()
    val id = tileIdRegex.matchEntire(lines[0])!!.groups[1]!!.value.toLong()

    TileData(id, lines.subList(1, lines.size))
}
