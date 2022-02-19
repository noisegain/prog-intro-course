package expression.exceptions

import expression.Operation

class LZeroes(inner: Operation) : AbstractUnary(inner, "l0") {
    override fun makeOperation(x: Int) =
        x.toUInt()
            .toString(2)
            .padStart(32, '0')
            .takeWhile { it == '0' }.length
}