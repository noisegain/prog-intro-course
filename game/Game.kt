package game

class Game(
    private val board: Board,
    private vararg val players: Player
) {
    fun play(log: Boolean = false): Int {
        while (true) {
            players.forEachIndexed { index, player ->
                val res = makeMove(player, index + 1, log)
                if (res != -1) {
                    println(board)
                    return res
                }
            }
        }
    }

    private fun makeMove(player: Player, no: Int, log: Boolean): Int {
        val move = player.makeMove(board.getPosition())
        val result = board.makeMove(move)
        if (log) {
            println()
            println("Player: $no")
            println(move)
            println(board)
            println("Result: $result")
        }
        return when (result) {
            GameResult.WIN -> no
            GameResult.LOSE -> players.size + 1 - no
            GameResult.DRAW -> 0
            GameResult.UNKNOWN -> -1
        }
    }
}