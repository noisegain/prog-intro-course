package markup

class Paragraph(a: List<MarkupInterface>) : AbstractMarkup(a) {
    fun toMarkdown(sb: StringBuilder) {
        for (a in content) {
            a.makeMarkdown(sb)
        }
    }

    fun toHtml(sb: StringBuilder) {
        for (a in content) {
            a.makeHTML(sb)
        }
    }
}