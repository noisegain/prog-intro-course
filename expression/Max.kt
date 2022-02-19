package expression

class Max(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "max") {
    override fun makeOperation(f: Int, s: Int) = maxOf(f, s)
}