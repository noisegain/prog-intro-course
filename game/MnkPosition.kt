package game

class MnkPosition(private val board: MnkBoard) : Position {
    override fun getTurn() = board.getTurn()

    override fun isValid(move: Move) = board.isValid(move)

    override fun toString() = board.toString()
}