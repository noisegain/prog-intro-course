package md2html

import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*

object Md2Html {
    private const val SEP = 0
    private const val PARAGRAPH = 1
    private const val HEADER = 2

    @JvmStatic
    fun main(args: Array<String>) {
        val res = StringBuilder()
        val paragraph = StringBuilder()
        val innerText = StringBuilder()
        val markup = Stack<MdPair>()
        val input = File(args[0]).readLines() as MutableList<String>
        val special = mapOf(
            '<' to "&lt;", '>' to "&gt;", '&' to "&amp;"
        )
        preprocessing(input)
        var header = 0
        var curState = SEP
        var lineState = false
        for (line in input) {
            if (line.isEmpty()) {
                paragraph.append(innerText)
                lineState = false
                if (curState == HEADER) {
                    paragraph.append("</h").append(header).append(">")
                } else if (curState == PARAGRAPH) {
                    paragraph.append("</p>")
                }
                if (innerText.isNotEmpty()) {
                    paragraph.append(System.lineSeparator())
                }
                innerText.setLength(0)
                curState = SEP
                continue
            }
            if (innerText.isNotEmpty()) {
                innerText.append(System.lineSeparator())
            }
            var pos = 0
            if (curState == SEP) {
                var headCnt = -1
                while (headCnt < line.length - 1 && line[++headCnt] == '#');
                if (headCnt > 0 && line[headCnt] == ' ') {
                    header = headCnt
                    pos = header
                    pos++
                    curState = HEADER
                    paragraph.append("<h").append(header).append(">")
                } else {
                    curState = PARAGRAPH
                    paragraph.append("<p>")
                }
            }
            if (lineState) {
                paragraph.append(System.lineSeparator())
                lineState = false
            }
            while (pos < line.length) {
                var c = line[pos]
                if ("*_-`'".indexOf(c) != -1) {
                    var cnt = 1
                    if (pos < line.length - 1) {
                        if (c == line[pos + 1]) {
                            cnt++
                            pos++
                        }
                    }
                    val cur = MdPair(c, cnt)
                    if (!markup.isEmpty()) {
                        if (markup.peek() == cur) {
                            markup.peek().addInterface(Text(innerText.toString()))
                            innerText.setLength(0)
                            val curInterface = markup.pop().markupInterface
                            if (markup.isEmpty()) {
                                curInterface.makeHTML(paragraph)
                                if (pos == line.length - 1) {
                                    lineState = true
                                }
                            } else {
                                markup.peek().addInterface(curInterface)
                                if (pos == line.length - 1) {
                                    markup.peek().addInterface(Text(System.lineSeparator()))
                                }
                            }
                        } else {
                            markup.peek().appendInner(innerText.toString())
                            innerText.setLength(0)
                            markup.add(cur)
                        }
                    } else {
                        paragraph.append(innerText)
                        innerText.setLength(0)
                        markup.add(cur)
                    }
                } else {
                    if (c == '\\') {
                        pos++
                    }
                    c = line[pos]
                    innerText.append(if (c in special) special[c] else c)
                }
                pos++
            }
        }
        res.append(paragraph)
        File(args[1]).printWriter(StandardCharsets.UTF_8).use { it.print(res) }
    }

    private fun preprocessing(input: MutableList<String>) {
        val checked: MutableSet<Pair<Int, Int>> = HashSet()
        repeat(3) {
            input.add("")
        }
        for (i in input.indices) {
            input[i] = buildString {
                var j = 0
                while (j < input[i].length) {
                    val c = input[i][j]
                    append(c)
                    if (c == '\\') {
                        append(input[i][++j])
                        j++
                        continue
                    }
                    if (Pair(i, j) in checked) {
                        j++
                        continue
                    }
                    if ("*_-`".indexOf(c) != -1) {
                        var len = 1
                        if (j + 1 < input[i].length && input[i][j + 1] == c) {
                            len++
                        }
                        if (!find(input, c, len, i, j + len, checked)) {
                            setLength(length - 1)
                            append('\\')
                            append(c)
                        }
                        if (len == 2) {
                            append(c)
                            j++
                        }
                    }
                    j++
                }
            }
        }
    }

    private fun find(
        input: List<String>, c: Char, len: Int, line: Int, start: Int, checked: MutableSet<Pair<Int, Int>>
    ): Boolean {
        for (i in line until input.size) {
            if (input[i].isEmpty()) return false
            var j = if (line == i) start else 0
            while (j < input[i].length) {
                val cur = input[i][j]
                if (cur == '\\') {
                    j += 2
                    continue
                }
                if (cur == c) {
                    if (len == 1) {
                        checked.add(Pair(i, j))
                        return true
                    } else if (j + 1 < input[i].length && input[i][j + 1] == c) {
                        checked.add(Pair(i, j))
                        checked.add(Pair(i, j + 1))
                        return true
                    }
                }
                j++
            }
        }
        return false
    }
}