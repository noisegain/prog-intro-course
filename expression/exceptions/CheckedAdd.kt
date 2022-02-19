package expression.exceptions

import expression.Operation

class CheckedAdd(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "+") {
    override fun makeOperation(f: Int, s: Int) =
        if (f < 0 && s < 0 && Int.MIN_VALUE - f > s
            || f > 0 && s > 0 && Int.MAX_VALUE - s < f) {
            throw OverflowException()
        } else {
            f + s
        }
}