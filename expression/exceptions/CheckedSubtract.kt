package expression.exceptions

import expression.Operation

class CheckedSubtract(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "-") {
    override fun makeOperation(f: Int, s: Int) =
        if (f == 0 && s == Int.MIN_VALUE
            || f < 0 && s > 0 && (f - s) >= 0
            || f > 0 && s < 0 && (f - s) <= 0
        ) {
            throw OverflowException()
        } else {
            f - s
        }
}