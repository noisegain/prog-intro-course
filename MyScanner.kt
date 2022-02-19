import java.io.*
import java.nio.charset.StandardCharsets

class MyScanner(private val input: BufferedReader) : Closeable {
    private val buffer = CharArray(128)
    private var pos = 0
    var line = 0
        private set
    private var read = 0
    private val sep = System.lineSeparator()
    private var nextIntUnderZero = false
    private var sepPos = 0

    internal constructor(input: InputStream?) : this(BufferedReader(InputStreamReader(input)))

    internal constructor(str: String) : this(BufferedReader(StringReader(str)))

    internal constructor(fileInputStream: FileInputStream?) : this(
        BufferedReader(
            InputStreamReader(
                fileInputStream, StandardCharsets.UTF_8
            )
        )
    )

    private fun skipSpaces(word: Boolean) {
        read()
        while (pos < read && !((Character.getType(buffer[pos]) == Character.DASH_PUNCTUATION.toInt() || buffer[pos].isLetter()) || !word && buffer[pos].isDigit() || buffer[pos] == '\'')) {
            if (!good(buffer[pos], 1)) line++
            pos++
            read()
        }
    }

    private fun good(x: Char, line: Int): Boolean {
        if (line == 1) {
            if (sep[sepPos++] != x) {
                sepPos = 0
            }
            if (sepPos == sep.length) {
                sepPos = 0
                return false
            }
            return true
        }
        return if (line == 2) {
            (buffer[pos].isLetter() || Character.getType(buffer[pos]) == Character.DASH_PUNCTUATION.toInt() || buffer[pos] == '\'')
        } else isInt(x) || x == '-'
    }

    private fun isInt(x: Char) = x.isDigit() || x in 'a'..'f'

    private fun token(type: Int): String? {
        val cur = StringBuilder()
        if (pos >= read) {
            read = input.read(buffer)
            if (read == -1) {
                return null
            }
            pos = 0
        }
        while (pos < read && good(buffer[pos], type)) {
            cur.append(buffer[pos++])
            read()
        }
        if (type == 1) {
            line++
            pos++
            if (sep.length == 2) {
                cur.setLength(cur.length - 1)
            }
        } else {
            skipSpaces(type == 2)
        }
        return cur.toString()
    }

    private fun read() {
        if (pos >= read) {
            read = input.read(buffer)
            pos = 0
        }
    }

    fun hasNextInt(): Boolean {
        read()
        while (pos < read && buffer[pos] == ' ') {
            pos++
            read()
        }
        if (pos >= read) {
            return false
        }
        if (isInt(buffer[pos])) {
            return true
        } else {
            if (buffer[pos] == '-') {
                nextIntUnderZero = true
                pos++
                if (pos < read) {
                    return isInt(buffer[pos])
                } else {
                    read()
                    if (read > 0) {
                        return isInt(buffer[0])
                    }
                }
            }
        }
        return false
    }

    fun readInt(hex: Boolean = false): Int {
        val res =
            if (!hex) token(0)!!.toInt()
            else token(0)!!.toUInt(16).toInt()
        if (nextIntUnderZero) {
            nextIntUnderZero = false
            return -res
        }
        return res
    }

    fun readLine(): String? {
        val res = token(1)
        if (nextIntUnderZero) {
            nextIntUnderZero = false
            return "-$res"
        }
        return res
    }

    fun readWord(): String? {
        skipSpaces(true)
        val res = token(2)
        return if (res != null && res.isNotEmpty()) res else null
    }

    override fun close() {
        input.close()
    }
}