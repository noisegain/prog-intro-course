object Reverse {
    private val out = arrayOfNulls<IntArray>(1000000)
    private val ans = IntArray(1000000)
    var hex = false

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) hex = false
        var sz = 0
        var m = 0
        val input = FastScanner(System.`in`)
        var next: String?
        var i = 0
        var line = 0
        fun move() {
            out[sz++] = ans.sliceArray(0 until i)
            m = m.coerceAtLeast(i)
            i = 0
            line++
        }
        while (input.read().also { next = it } != null) {
            if (next!!.isEmpty()) {
                move()
            } else {
                ans[i++] = if (hex) next!!.toUInt(16).toInt() else next!!.toInt()
                if (input.line > line) {
                    move()
                }
            }
        }
        if (args.size == 1) {
            for (j in 0 until m) {
                for (v in 0 until sz) {
                    if (out[v]!!.size > j) {
                        print("${out[v]!![j]} ")
                    }
                }
                println()
            }
        } else {
            for (v in sz - 1 downTo 0) {
                for (j in out[v]!!.indices.reversed()) {
                    print("${out[v]!![j]} ")
                }
                println()
            }
        }
    }
}