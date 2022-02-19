package expression.exceptions

import expression.Operation

class TZeroes(inner: Operation) : AbstractUnary(inner, "t0") {
    override fun makeOperation(x: Int) =
        x.toUInt()
            .toString(2).reversed()
            .padEnd(32, '0')
            .takeWhile { it == '0' }.length
}