package expression.exceptions

import expression.Operation

class CheckedNegate(inner: Operation) : AbstractUnary(inner, "-") {
    override fun makeOperation(x: Int): Int {
        if (x == Int.MIN_VALUE) {
            throw OverflowException()
        }
        return -x
    }
}