package markup

class Quote(a: List<MarkupInterface>) : AbstractMarkup(a) {
    init {
        markdownSep = "''"
        htmlSep = "q"
    }
}