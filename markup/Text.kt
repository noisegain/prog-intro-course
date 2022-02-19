package markup

class Text : MarkupInterface {
    private val text: String

    constructor(text: String) {
        this.text = text
    }

    constructor(content: List<MarkupInterface>) {
        val str = StringBuilder()
        for (a in content) {
            a.makeHTML(str)
        }
        text = str.toString()
    }

    override fun makeMarkdown(sb: StringBuilder) {
        sb.append(text)
    }

    override fun makeHTML(sb: StringBuilder) {
        sb.append(text)
    }
}