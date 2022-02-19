package expression.exceptions

import expression.Operation

abstract class AbstractUnary(
    protected val inner: Operation,
    protected val op: String,
) : Operation {

    override fun evaluate(x: Int) =
        makeOperation(inner.evaluate(x))

    override fun evaluate(x: Int, y: Int, z: Int) =
        makeOperation(inner.evaluate(x, y, z))

    abstract fun makeOperation(x: Int): Int

    override fun toString() = "$op($inner)"


    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as AbstractUnary
        return inner == other.inner
    }

    override fun hashCode() =
        inner.hashCode() * 7 + op.hashCode()
}