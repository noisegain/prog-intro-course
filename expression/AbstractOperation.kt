package expression

abstract class AbstractOperation(
    protected val f: Operation,
    protected val s: Operation,
    protected val op: String,
) : Operation {

    override fun evaluate(x: Int): Int =
        makeOperation(f.evaluate(x), s.evaluate(x))

    override fun evaluate(x: Int, y: Int, z: Int) =
        makeOperation(f.evaluate(x, y, z), s.evaluate(x, y, z))

    abstract fun makeOperation(f: Int, s: Int): Int

    override fun toString() = "($f $op $s)"


    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as AbstractOperation
        return f == other.f && s == other.s
    }

    override fun hashCode() =
        f.hashCode() * 49 + s.hashCode() * 7 + op.hashCode()
}