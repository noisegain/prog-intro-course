package game

class MnkBoard(
    private val m: Int,
    private val n: Int,
    private val k: Int,
    private val players: Int
) : Board {

    private val field = Array(n) {
        Array(m) { Cell.E }
    }

    private val position = MnkPosition(this)
    private val turns = listOf(Cell.X, Cell.O, Cell.I, Cell.L).subList(0, players)
    private var turnPos = 0
    private var turn = turns[0]
    fun getTurn() = turn

    private var emptyCnt = m * n
    private val spacesCnt = m.toString().length

    override fun makeMove(move: Move): GameResult {
        if (!isValid(move)) {
            return GameResult.LOSE
        }
        field[move.row][move.col] = move.value
        emptyCnt--
        if (checkWin(move)) {
            return GameResult.WIN
        }
        changeTurn()
        if (emptyCnt == 0) {
            return GameResult.DRAW
        }
        return GameResult.UNKNOWN
    }

    private fun changeTurn() {
        turnPos = ++turnPos % players
        turn = turns[turnPos]
    }

    private fun checkWin(move: Move): Boolean = with(move) {
        checkLine(row, col) || checkDiag(row, col)
    }

    private fun checkCnt(i: Int, j: Int, count: Int) =
        if (field[i][j] == turn) count + 1 else 0

    private fun checkDiag(row: Int, col: Int): Boolean {
        var count = 0
        fun winReturn(i: Int, j: Int): Boolean {
            count = checkCnt(i, j, count)
            return count == k
        }

        var i = maxOf(row - k, -1)
        var j = maxOf(col - k, -1)
        val r = (row - i) - (col - j)
        when {
            r < 0 ->
                j = col - row + i
            r > 0 ->
                i = row - col + j
        }
        val endN = minOf(row + k, n)
        val endM = minOf(col + k, m)
        while (++i < endN && ++j < endM) {
            if (winReturn(i, j)) {
                return true
            }
        }
        i = row - k
        j = col + k
        while (++i < endN && --j >= 0) {
            if (i >= 0 && j < endM) {
                if (winReturn(i, j)) {
                    return true
                }
            } else {
                count = 0
            }
        }
        return false
    }

    private fun checkLine(row: Int, col: Int): Boolean {
        var count = 0
        var count2 = 0
        return (maxOf(row - k + 1, 0) until minOf(row + k, n)).any {
            count = checkCnt(it, col, count)
            count == k
        } || (maxOf(col - k + 1, 0) until minOf(col + k, m)).any {
            count2 = checkCnt(row, it, count2)
            count2 == k
        }
    }

    override fun isValid(move: Move) =
        move.row in 0 until n && move.col in 0 until m
                && field[move.row][move.col] == Cell.E
                && turn == move.value

    override fun getPosition() = position

    private fun spaces(n: Int) = " ".repeat(n)

    override fun toString() = buildString {
        append(spaces(spacesCnt + 1))
        (1..m).forEach {
            append(it)
            append(spaces(spacesCnt - it.toString().length + 1))
        }
        appendLine()
        (0 until n).forEach { i ->
            append(i + 1)
            append(spaces(spacesCnt))
            (0 until m).forEach { j ->
                append(
                    cellToString(field[i][j])
                )
                append(spaces(spacesCnt))
            }
            appendLine()
        }
    }
}
