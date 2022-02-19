package expression.parser

import expression.exceptions.ParserException

abstract class BaseParser(private val source: CharSource) {
    private var ch = END
    private var prev = END

    init { take() }

    protected fun take(): Char {
        prev = ch
        ch = if (source.hasNext()) source.next() else {
            source.step()
            END
        }
        return prev
    }

    protected fun stepBack() {
        ch = source.stepBack() ?: ' '
    }
    protected fun test(expected: Char) =
        ch == expected

    protected fun take(expected: Char) =
        if (test(expected)) {
            take()
            true
        } else {
            false
        }

    protected fun expect(expected: Char) {
        if (!take(expected)) {
            throw ParserException("Expected '$expected', found '$ch'")
        }
    }

    protected fun expect(value: String) {
        for (c in value) {
            expect(c)
        }
    }

    protected fun eof() = take(END)

    fun ws() = ch.isWhitespace()

    protected fun error(message: String?): Throwable = source.error(message)

    protected fun between(from: Char, to: Char) = ch in from..to

    companion object {
        private const val END = '\uffff'
    }
}