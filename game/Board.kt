package game

interface Board {
    fun makeMove(move: Move): GameResult
    fun isValid(move: Move): Boolean
    fun getPosition(): Position
}