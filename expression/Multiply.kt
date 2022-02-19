package expression

class Multiply(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "*") {
    override fun makeOperation(f: Int, s: Int) = f * s
}