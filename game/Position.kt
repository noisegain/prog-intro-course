package game

interface Position {
    fun getTurn(): Cell
    fun isValid(move: Move): Boolean
}