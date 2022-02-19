package expression.parser

fun main() {
    val parser = expression.exceptions.ExpressionParser()
    println(parser.parse("-(-(z))"))
}