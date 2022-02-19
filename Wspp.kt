import java.io.File
import java.io.FileInputStream
import kotlin.jvm.JvmStatic
import java.util.TreeMap
import kotlin.Pair

object Wspp {
    @JvmStatic
    fun main(args: Array<String>) {
        val dict: MutableMap<String, MutableList<Int>> = LinkedHashMap()
        var cur: String?
        var i = 0
        MyScanner(FileInputStream(args[0])).use { input ->
            while (input.readWord().also { cur = it } != null) {
                val c = cur!!.lowercase()
                val temp = dict.getOrDefault(c, mutableListOf())
                temp.add(++i)
                dict.putIfAbsent(c, temp)
            }
        }
        File(args[1]).printWriter().use { out ->
            for (v in dict.keys) {
                val arr = dict[v]
                out.print(v + " " + arr!!.size)
                for (j in arr.indices) {
                    out.print(" " + arr[j])
                }
                out.println()
            }
        }
    }
}