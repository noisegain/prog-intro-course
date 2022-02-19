package game

class SequentialPlayer(private val m: Int, private val n: Int) : Player {
    override fun makeMove(position: Position): Move {
        for (r in 0 until n) {
            for (c in 0 until m) {
                val move = Move(r, c, position.getTurn())
                if (position.isValid(move)) {
                    return move
                }
            }
        }
        throw IllegalStateException("No valid moves")
    }
}