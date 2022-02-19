package md2html

interface MarkupInterface {
    fun makeMarkdown(sb: StringBuilder)
    fun makeHTML(sb: StringBuilder)
}