package markup

class Emphasis(a: List<MarkupInterface>) : AbstractMarkup(a) {
    init {
        markdownSep = "*"
        htmlSep = "em"
    }
}