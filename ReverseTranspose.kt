import kotlin.jvm.JvmStatic

object ReverseTranspose {

    @JvmStatic
    fun main(args: Array<String>) {
        Reverse.hex = false
        Reverse.main(arrayOf(" "))
    }
}