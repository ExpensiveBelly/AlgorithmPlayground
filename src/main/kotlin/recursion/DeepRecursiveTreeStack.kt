package recursion


private class Tree(val left: Tree?, val right: Tree?)

@ExperimentalStdlibApi
private fun Tree?.depthStackInHeap(): Int {
    if (this == null) return 0
    class Frame(val node: Tree, var state: Int = 0, var depth: Int = 1)

    val stack = ArrayList<Frame>()
    val root = Frame(this)
    stack.add(root)
    while (stack.isNotEmpty()) {
        val frame = stack.last()
        when (frame.state++) {
            0 -> frame.node.left?.let { l -> stack.add(Frame(l)) }
            1 -> frame.node.right?.let { r -> stack.add(Frame(r)) }
            2 -> {
                stack.removeLast()
                stack.lastOrNull()?.let { p -> p.depth = maxOf(p.depth, frame.depth + 1) }
            }
        }
    }
    return root.depth
}

private fun Tree?.depthStackOverflow(): Int =
    if (this == null) 0 else maxOf(
        left.depthStackOverflow(), // recursive call one
        right.depthStackOverflow() // recursive call two
    ) + 1

@ExperimentalStdlibApi
fun main() {
    val n = 100_000
    val deepTree = generateSequence(Tree(null, null)) { prev ->
        Tree(prev, null)
    }.take(n).last()

    println(deepTree.depthStackInHeap())
    println(deepTree.depthStackOverflow())
}

