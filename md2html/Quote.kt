package md2html

class Quote(a: List<MarkupInterface>) : AbstractMarkup(a) {
    init {
        markdownSep = "''"
        htmlSep = "q"
    }
}