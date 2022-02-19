package markup

abstract class AbstractMarkup protected constructor(protected val content: List<MarkupInterface>) : MarkupInterface {
    protected var markdownSep: String = ""
    protected var htmlSep: String = ""

    override fun makeMarkdown(sb: StringBuilder) {
        sb.append(markdownSep)
        for (a in content) {
            a.makeMarkdown(sb)
        }
        sb.append(markdownSep)
    }

    override fun makeHTML(sb: StringBuilder) {
        sb.append('<').append(htmlSep).append('>')
        for (a in content) {
            a.makeHTML(sb)
        }
        sb.append("</").append(htmlSep).append('>')
    }
}