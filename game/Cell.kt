package game

enum class Cell {
    E, X, O, I, L
}

fun cellToString(cell: Cell) =
    when (cell) {
        Cell.E -> '.'
        Cell.X -> 'X'
        Cell.O -> 'O'
        Cell.I -> '|'
        Cell.L -> '_'
    }
