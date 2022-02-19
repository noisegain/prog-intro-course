package expression.exceptions

import expression.Operation

class Min(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "min") {
    override fun makeOperation(f: Int, s: Int) = minOf(f, s)
}