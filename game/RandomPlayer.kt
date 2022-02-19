package game

import java.util.*

class RandomPlayer(private val m: Int, private val n: Int) : Player {
    override fun makeMove(position: Position): Move {
        while (true) {
            val move = with(Random()) {
                Move(nextInt(m), nextInt(n), position.getTurn())
            }
            if (position.isValid(move)) {
                return move
            }
        }
    }
}