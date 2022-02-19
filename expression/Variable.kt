package expression

class Variable(private val name: String) : Operation {
    override fun evaluate(x: Int) = x

    override fun evaluate(x: Int, y: Int, z: Int) =
        when (name) {
            "x" -> x
            "y" -> y
            "z" -> z
            else -> throw AssertionError("Not supported")
        }

    override fun toString() = name

    override fun equals(other: Any?) =
        other is Variable && other.name == name

    override fun hashCode() = name.hashCode()
}