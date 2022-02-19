package md2html

class Strong(a: List<MarkupInterface>) : AbstractMarkup(a) {
    init {
        markdownSep = "__"
        htmlSep = "strong"
    }
}