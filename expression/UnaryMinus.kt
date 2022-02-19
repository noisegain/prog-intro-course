package expression

class UnaryMinus(private val op: Operation) : Operation {
    override fun evaluate(x: Int) = -op.evaluate(x)

    override fun evaluate(x: Int, y: Int, z: Int) = -op.evaluate(x, y, z)

    override fun toString() = "-($op)"

    override fun hashCode() = op.hashCode() * 7 + 1

    override fun equals(other: Any?) =
        other is UnaryMinus && other.op == op
}