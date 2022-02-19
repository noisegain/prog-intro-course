package expression.exceptions

fun main() {
    val parser = ExpressionParser()
    val x = parser.parse("(2+1)")
    println(x.evaluate(0))
}