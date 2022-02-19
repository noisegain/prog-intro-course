import java.io.InputStream

class FastScanner(private val input: InputStream) {
    private val buffer = ByteArray(SIZE)
    private var pos = 0
    private var read = 0
    private val sep = System.lineSeparator()
    var line = 0
        private set

    private tailrec fun readChar(): Char {
        if (pos == read) {
            pos = 0
            read = input.read(buffer)
        }
        return if (read < 0) END
        else {
            val c = buffer[pos++].toInt().toChar()
            if (c in sep && c != sep.last()) readChar()
            else c
        }
    }

    fun readLine(): String? {
        var c = readChar()
        return if (c == END) null
        else buildString {
            while (true) {
                when (c) {
                    '\n', END -> return@buildString
                    else -> {
                        append(c)
                        c = readChar()
                    }
                }
            }
        }
    }

    fun read(): String? {
        var c: Char
        while (readChar().also {
                c = it
                if (it == '\n') {
                    line++
                    return ""
                }
            }.isWhitespace());
        if (c == END) return null
        return buildString {
            do {
                append(c)
                c = readChar()
            } while (c.isLetterOrDigit())
            if (c == '\n') line++
        }
    }

    companion object {
        private const val SIZE = 1 shl 7
        private const val END = '\u0000'
    }
}