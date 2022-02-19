class PairIntArray {
    var size = 0
        private set
    private var arr = arrayOfNulls<Pair<Int, Int>>(10)
    fun add(value: Pair<Int, Int>?) {
        if (size == arr.size) {
            arr = arr.copyOf(3 * arr.size / 2)
        }
        arr[size++] = value
    }

    operator fun get(position: Int): Pair<Int, Int>? {
        if (position >= size || position < 0) {
            throw IndexOutOfBoundsException("bad position")
        }
        return arr[position]
    }
}