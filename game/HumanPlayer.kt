package game

import java.util.*

class HumanPlayer : Player {
    private val input = Scanner(System.`in`)

    override fun makeMove(position: Position): Move {
        println("Position")
        println(position)
        println("${cellToString(position.getTurn())}'s move")
        while (true) {
            print("Enter row and column: ")
            val first = input.next()
            val second = input.next()
            val move =
                try {
                    Move(
                        first.toInt() - 1,
                        second.toInt() - 1,
                        position.getTurn()
                    )
                } catch (e: NumberFormatException) {
                    Move(-1, -1, position.getTurn())
                }
            if (position.isValid(move)) {
                return move
            }
            println("Move $move is invalid")
        }
    }
}