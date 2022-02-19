package expression

class Add(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "+") {
    override fun makeOperation(f: Int, s: Int): Int = f + s
}