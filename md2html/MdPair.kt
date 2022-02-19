package md2html

class MdPair(var first: Char, var second: Int) {
    private val inner = StringBuilder()
    private val content = mutableListOf<MarkupInterface>()

    fun addInterface(content: MarkupInterface) {
        this.content.add(content)
    }

    fun appendInner(s: String) {
        inner.append(s)
    }

    val markupInterface: MarkupInterface
        get() {
            content.add(0, Text(inner.toString()))
            if (first == '_' || first == '*') {
                return if (second == 2) Strong(content) else Emphasis(content)
            }
            if (first == '\'') {
                if (second == 2) {
                    return Quote(content)
                }
                content.add(0, Text("'"))
                content.add(Text("'"))
                return Text(content)
            }
            return if (first == '`') Code(content) else Strikeout(content)
        }

    override fun equals(other: Any?): Boolean =
        other is MdPair && first == other.first && second == other.second

    override fun hashCode(): Int =
        31 * first.hashCode() + second
}