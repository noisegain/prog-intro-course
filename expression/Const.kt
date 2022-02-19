package expression

class Const(val c: Int) : Operation {
    override fun evaluate(x: Int) = c

    override fun evaluate(x: Int, y: Int, z: Int) = c

    override fun toString() = c.toString()

    override fun equals(other: Any?) =
        other is Const && other.c == c

    override fun hashCode() = c
}