package markup

interface MarkupInterface {
    fun makeMarkdown(sb: StringBuilder)
    fun makeHTML(sb: StringBuilder)
}