package helper

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun minus(other: Point3D): Point3D {
        return Point3D(
            x - other.x, y - other.y, z - other.z
        )
    }

    operator fun plus(other: Point3D): Point3D {
        return Point3D(
            x + other.x, y + other.y, z + other.z
        )
    }

    val neighbours: List<Point3D> by lazy {
        val range = -1..1
        val list = mutableListOf<Point3D>()
        for (xOffset in range) {
            for (yOffset in range) {
                for (zOffset in range) {
                    if (xOffset != 0 || yOffset != 0 || zOffset != 0) {
                        list.add(Point3D(x + xOffset, y + yOffset, z + zOffset))
                    }
                }
            }
        }
        list
    }

}

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) {
    operator fun minus(other: Point4D): Point4D {
        return Point4D(
            x - other.x, y - other.y, z - other.z, w - other.w
        )
    }

    operator fun plus(other: Point4D): Point4D {
        return Point4D(
            x + other.x, y + other.y, z + other.z, w - other.w
        )
    }

    val neighbours: List<Point4D> by lazy {
        val range = -1..1
        val list = mutableListOf<Point4D>()
        for (xOffset in range) {
            for (yOffset in range) {
                for (zOffset in range) {
                    for (wOffset in range) {
                        if (xOffset != 0 || yOffset != 0 || zOffset != 0 || wOffset != 0) {
                            list.add(Point4D(x + xOffset, y + yOffset, z + zOffset, w + wOffset))
                        }
                    }
                }
            }
        }
        list
    }

}