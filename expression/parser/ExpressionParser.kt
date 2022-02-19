package expression.parser

import expression.*

class ExpressionParser : Parser {
    override fun parse(expression: String) =
        ExprParser(expression).parse()

    class ExprParser(expression: String) : BaseParser(StringSource(expression)) {
        class OperationHandler(val op: Char) : Operation {
            override fun evaluate(x: Int) = 0
            override fun evaluate(x: Int, y: Int, z: Int) = 0

            val priority =
                when (op) {
                    in "nx" -> -1
                    in "-+" -> 0
                    in "*/" -> 1
                    else -> throw error("No such operation")
                }
        }

        fun parse(): Operation {
            val result = parseElement()
            skipWS()
            if (!eof()) throw error("EOF not find")
            return result
        }

        private fun parseVarOrConst(underZero: Boolean): Operation {
            skipWS()
            if (between('0', '9')) {
                val value = buildString {
                    if (underZero) append("-")
                    while (true) {
                        append(('0'..'9').firstOrNull(::take) ?: break)
                    }
                }
                if (value.toLong() == -Int.MIN_VALUE.toLong()) {
                    return Const("-$value".toInt())
                }
                return Const(value.toInt())
            }
            val res = Variable(
                ("xyz".firstOrNull(::take)
                    ?: throw error("Expected Variable")).toString()
            )
            return if (underZero) UnaryMinus(res) else res
        }

        private fun parseType(): Char? =
            parseShortType() ?: parseLongType()

        private fun parseShortType(): Char? =
            "*/+-".firstOrNull(::take)

        private fun parseLongType(): Char? =
            if (take('m')) {
                if ("in".all(::take)) {
                    'n'
                } else if ("ax".all(::take)) {
                    'x'
                } else {
                    throw error("min or max expected")
                }
            } else {
                null
            }

        private fun parseOperation(): Operation? {
            if (eof()) return null
            var count = 0
            while (take('-')) {
                count++
                skipWS()
            }
            val inner = take('(')
            if (inner) {
                val res = parseElement()
                expect(')')
                return if (count % 2 == 1) UnaryMinus(res) else res
            }
            return parseVarOrConst(count % 2 == 1)
        }

        private fun parseElement(): Operation {
            val list = mutableListOf<Operation>()
            while (true) {
                skipWS()
                list += parseOperation() ?: break
                skipWS()
                list += OperationHandler(parseType() ?: break)
            }
            return evaluate(list)
        }

        private fun evaluate(content: List<Operation>): Operation {
            var ans = mutableListOf<Operation>()
            ans.addAll(content)
            for (priority in 1 downTo -1) {
                if (ans.size == 1) break
                val newList = mutableListOf<Operation>()
                for (i in 0 until ans.size - 2 step 2) {
                    val (f, op, s) = ans.subList(i, i + 3)
                    if (newList.isNotEmpty() && newList.last() !is OperationHandler) {
                        newList.removeLast()
                    }
                    if ((op as OperationHandler).priority == priority) {
                        val res = operation(s, op, f)
                        ans[i + 2] = res
                        newList.add(res)
                    } else {
                        newList.addAll(listOf(f, op, s))
                    }
                }
                ans = newList
            }
            return ans.first()
        }

        private fun operation(s: Operation, op: Operation, f: Operation): Operation {
            if (op !is OperationHandler) {
                throw error("Incorrect input")
            }
            return when (op.op) {
                '-' -> Subtract(f, s)
                '+' -> Add(f, s)
                '*' -> Multiply(f, s)
                '/' -> Divide(f, s)
                'n' -> Min(f, s)
                'x' -> Max(f, s)
                else -> throw error("Incorrect input")
            }
        }

        private fun skipWS() {
            while (ws()) {
                take()
            }
        }
    }
}