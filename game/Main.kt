package game

fun main() {
    val m = 4
    val n = 4
    val k = 3
    val res = Game(
        MnkBoard(m, n, k, 4),
        SequentialPlayer(m, n),
        RandomPlayer(m, n),
        SequentialPlayer(m, n),
        HumanPlayer()
    ).play(log = true)
    println(
        when (res) {
            in 1..4 -> "$res player won"
            0 -> "Draw"
            else -> throw AssertionError("Unknown result: $res")
        }
    )
}