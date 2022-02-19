package expression.parser

class StringSource(private val data: String) : CharSource {
    private var pos = 0

    override fun hasNext() = pos < data.length

    override fun next() = data[pos++]

    override fun stepBack(): Char? {
        return data.getOrNull(--pos - 1)
    }

    override fun step() { pos++ }

    override fun error(message: String?) =
        IllegalArgumentException("$pos: $message")
}