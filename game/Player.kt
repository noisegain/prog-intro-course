package game

interface Player {
    fun makeMove(position: Position): Move
}