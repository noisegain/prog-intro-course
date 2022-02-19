package expression.exceptions

import expression.Operation

class CheckedDivide(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "/") {
    override fun makeOperation(f: Int, s: Int): Int {
        if (f == Int.MIN_VALUE && s == -1) {
            throw OverflowException()
        }
        if (s == 0) {
            throw ArithmeticException("Divide by zero")
        }
        return f / s
    }
}