package md2html

class Strikeout(a: List<MarkupInterface>) : AbstractMarkup(a) {
    init {
        markdownSep = "~"
        htmlSep = "s"
    }
}