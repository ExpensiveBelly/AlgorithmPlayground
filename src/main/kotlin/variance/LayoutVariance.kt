package variance

/**
 * https://gist.github.com/breandan/1780834bc24d70d9e721c975d4cc84c1
 */

fun main(args: Array<String>) {
    val horizontal = Layout(Horizontal)
    val vertical = Layout(Vertical)

    // Type safe rotation!
    var v: Layout<Vertical> = horizontal.rotate()
    var h: Layout<Horizontal> = vertical.rotate()
    h = horizontal.rotate().rotate()
    v = vertical.rotate().rotate()

    /* Does not compile!
    v = horizontal.rotate().rotate()
    h = vertical.rotate().rotate()
    */

    println("Horizontal rotated is $v")
    println("Vertical rotated is $h")
}

interface Orientation {
    fun rotate(): Orientation
}

object Vertical : Orientation {
    override fun rotate() = Horizontal
    override fun toString() = "Vertical"
}

object Horizontal : Orientation {
    override fun rotate() = Vertical
    override fun toString() = "Horizontal"
}

@JvmName("rotateVertical")
fun Layout<Horizontal>.rotate() = Layout<Vertical>(this.t.rotate())

@JvmName("rotateHorizontal")
fun Layout<Vertical>.rotate() = Layout<Horizontal>(this.t.rotate())

class Layout<out T : Orientation>(val t: T) {
    override fun toString() = t.toString()
}

open class Builder<out T : Layout<*>> {
    open fun build(): T = build()
}

open class Renderer<in T> {
    open fun render(t: T) {
        println(t)
    }
}

fun covariance(horizontalBuilder: Builder<Layout<Horizontal>>,
               layoutBuilder: Builder<Layout<Orientation>>) {
    // Layout<Horizontal> is a subtype of Layout<Orientation> ✓
    val bldr: Builder<Layout<Orientation>> = horizontalBuilder

    // Layout<Orientation> is a supertype of Layout<Horizontal> ✗
//    val hlbr: Builder<Layout<Horizontal>> = layoutBuilder //ERROR
}

fun contrav(layoutRenderer: Renderer<Layout<Orientation>>,
            horizontalRenderer: Renderer<Layout<Horizontal>>) {
    // Layout<Orientation> is a supertype of Layout<Horizontal> ✓
    val hlrd: Renderer<Layout<Horizontal>> = layoutRenderer

    // Layout<Horizontal> is a subtype of Layout<Orientation> ✗
//    val lrdr: Renderer<Layout<Orientation>> = horizontalRenderer
}