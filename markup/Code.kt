package markup

class Code(a: List<MarkupInterface>) : AbstractMarkup(a) {
    init {
        markdownSep = "`"
        htmlSep = "code"
    }
}