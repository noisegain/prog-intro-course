package expression.exceptions

import expression.parser.*
import expression.Operation
import expression.Const
import expression.Variable

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
                    else -> throw ParserException("No such operation")
                }
        }

        enum class UnaryHandler {
            MINUS, T, L
        }

        fun parse(): Operation {
            val result = parseElement()
            skipWS()
            if (!eof()) throw ParserException("EOF not find")
            return result
        }

        private fun parseVarOrConst(): Operation {
            skipWS()
            val underZero = take('-')
            if (between('0', '9')) {
                val value = buildString {
                    if (underZero) append('-')
                    while (true) {
                        append(('0'..'9').firstOrNull(::take) ?: break)
                    }
                }
                try {
                    return Const(value.toInt())
                } catch (e: NumberFormatException) {
                    throw ParserException("Constant overflow")
                }
            }
            return Variable(
                ("xyz".firstOrNull(::take)
                    ?: throw ParserException("Expected Variable")).toString()
            )
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
                    throw ParserException("min or max expected")
                }
            } else {
                null
            }

        private fun parseUnary(): UnaryHandler? {
            if (eof()) throw ParserException("Unexpected eof")
            return when(val c = take()) {
                '-' -> {
                    if (between('0', '9')) {
                        stepBack()
                        null
                    }
                    else UnaryHandler.MINUS
                }
                in "tl" -> {
                    expect('0')
                    if (take('(')) {
                        stepBack()
                    } else {
                        expect(' ')
                    }
                    if (c == 't') UnaryHandler.T else UnaryHandler.L
                }
                in '0'..'9', in "xyz()" -> {
                    stepBack()
                    null
                }
                else -> {
                    throw ParserException("Unknown unary operation")
                }
            }
        }

        private fun parseUnaries(): List<UnaryHandler> {
            val res = mutableListOf<UnaryHandler>()
            while (true) {
                skipWS()
                res += parseUnary() ?: return res
            }
        }

        private fun parseElement(): Operation {
            val list = mutableListOf<Operation>()
            while (true) {
                skipWS()
                val unaries = parseUnaries()
                skipWS()
                val next: Operation
                if (take('(')) {
                    next = parseElement()
                    expect(')')
                } else {
                    next = parseVarOrConst()
                }
                var unary = next
                for (op in unaries.asReversed()) {
                    unary = when(op) {
                        UnaryHandler.MINUS -> CheckedNegate(unary)
                        UnaryHandler.T -> TZeroes(unary)
                        UnaryHandler.L -> LZeroes(unary)
                    }
                }
                list += unary
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
                throw ParserException("Incorrect input")
            }
            return when (op.op) {
                '-' -> CheckedSubtract(f, s)
                '+' -> CheckedAdd(f, s)
                '*' -> CheckedMultiply(f, s)
                '/' -> CheckedDivide(f, s)
                'n' -> Min(f, s)
                'x' -> Max(f, s)
                else -> throw ParserException("Incorrect input")
            }
        }

        private fun skipWS() {
            while (ws()) {
                take()
            }
        }
    }
}