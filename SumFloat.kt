object SumFloat {
    @JvmStatic
    fun main(args: Array<String>) {
        var ans = 0f
        for (x in args) {
            var start = 0
            var i = 0
            for (c in x) {
                if (c.isWhitespace()) {
                    if (i > start) ans += x.substring(start, i).toFloat()
                    start = i + 1
                }
                i++
            }
            if (i > start) ans += x.substring(start, i).toFloat()
        }
        println(ans)
    }
}