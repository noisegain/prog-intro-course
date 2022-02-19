package expression.parser

interface CharSource {
    operator fun hasNext(): Boolean
    operator fun next(): Char
    fun stepBack(): Char?
    fun error(message: String?): Throwable
    fun step()
}