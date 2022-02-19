package expression

class Divide(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "/") {
    override fun makeOperation(f: Int, s: Int) = f / s
}