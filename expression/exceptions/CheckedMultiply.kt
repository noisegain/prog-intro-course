package expression.exceptions

import expression.Operation

class CheckedMultiply(
    f: Operation,
    s: Operation
) : AbstractOperation(f, s, "*") {
    override fun makeOperation(f: Int, s: Int) = (f * s).let {
        if (f != 0 && s != 0 && (it / f != s || it / s != f)) {
            throw OverflowException()
        } else {
            it
        }
    }
}