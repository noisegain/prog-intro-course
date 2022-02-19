import java.io.File
import java.io.FileInputStream
import kotlin.jvm.JvmStatic
import java.util.TreeMap

object WsppSortedPosition {
    @JvmStatic
    fun main(args: Array<String>) {
        val dict: MutableMap<String, PairIntArray> = TreeMap()
        var cur: String?
        var i = 0
        MyScanner(FileInputStream(args[0])).use { input ->
            var line = input.line
            while (input.readWord().also { cur = it } != null) {
                val c = cur!!.lowercase()
                val temp = dict.getOrDefault(c, PairIntArray())
                temp.add(Pair(line + 1, ++i))
                dict.putIfAbsent(c, temp)
                if (line != input.line.also { line = it }) {
                    i = 0
                }
            }
        }
        File(args[1]).printWriter().use { out ->
            for (v in dict.keys) {
                val arr = dict[v]
                out.print(v + " " + arr!!.size)
                for (j in 0 until arr.size) {
                    out.print(" " + arr[j]!!.first + ":" + arr[j]!!.second)
                }
                out.println()
            }
        }
    }
}