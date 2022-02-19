import java.io.*
import java.util.*
import kotlin.collections.LinkedHashMap

object WordStatInput {
    @JvmStatic
    fun main(args: Array<String>) {
        val dict = LinkedHashMap<String, Int>()
        val s = StringBuilder()
        BufferedReader(
            InputStreamReader(
                FileInputStream(args[0]),
                "utf8"
            )
        ).forEachLine {
            s.setLength(0)
            for (x in it.toCharArray()) {
                if (Character.isLetter(x) || Character.getType(x) == Character.DASH_PUNCTUATION.toInt() || x == '\'') {
                    s.append(x)
                } else {
                    if (s.isNotEmpty()) {
                        dict.merge(s.toString().lowercase(), 1, Int::plus)
                        s.setLength(0)
                    }
                }
            }
            if (s.isNotEmpty()) {
                dict.merge(s.toString().lowercase(), 1, Int::plus)
            }
        }
        BufferedWriter(
            OutputStreamWriter(
                FileOutputStream(args[1]),
                "utf8"
            )
        ).use { out ->
            val ans = if (args.size == 2) {
                dict.entries
            } else {
                dict.entries.sortedBy { it.value }
            }
            for ((k, v) in ans) {
                out.write("$k $v")
                out.newLine()
            }
        }
    }
}