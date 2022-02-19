package expression

import expression.parser.ExpressionParser

fun main() {
    println(ExpressionParser().parse("-(-2147483648)"))
}
/*
(((y max 51194599)-(y / y))max((y min y)/(x - 928656827)))
(((y max 51194599)-(y / y))max((y min y)/(x- 928656827)))
(((y max 51194599)-(y / y))max((y min y)/(x- 928656827)))
 */