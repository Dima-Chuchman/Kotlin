package lab04


import kotlin.math.sqrt
import kotlin.random.Random

sealed interface Roots
data class TwoRoots(val x1: Double, val x2: Double) : Roots
data class OneRoot(val x: Double) : Roots
data object NoRealRoots : Roots

class QuadraticEquation(a: Double, var b: Double, var c: Double) {

    var a: Double = a
        set(value) {
            require(value != 0.0)
            field = value
        }

    init {
        require(this.a != 0.0)
    }

    constructor(a: Int, b: Int, c: Int) : this(a.toDouble(), b.toDouble(), c.toDouble())

    constructor(b: Double, c: Double) : this(1.0, b, c)

    val discriminant: Double
        get() = b * b - 4 * a * c

    override fun toString(): String {
        return "${a}x^2 + ${b}x + $c = 0"
    }

    fun solve(): Roots {
        val d = discriminant
        if (d > 0) {
            val x1 = (-b + sqrt(d)) / (2 * a)
            val x2 = (-b - sqrt(d)) / (2 * a)
            return TwoRoots(x1, x2)
        }
        if (d == 0.0) {
            val x = -b / (2 * a)
            return OneRoot(x)
        }
        return NoRealRoots
    }
}

fun main() {
    val equations = mutableListOf<QuadraticEquation>()

    for (i in 1..100) {
        var randomA = Random.nextInt(-50, 51)
        while (randomA == 0) {
            randomA = Random.nextInt(-50, 51)
        }
        val randomB = Random.nextInt(-50, 51)
        val randomC = Random.nextInt(-50, 51)

        equations.add(QuadraticEquation(randomA, randomB, randomC))
    }

    val filtered = equations.filter { it.discriminant > 0 }

    filtered.forEach { eq ->
        val roots = eq.solve()
        print(eq.toString() + " має корені: ")
        when (roots) {
            is TwoRoots -> println("x1 = ${roots.x1}, x2 = ${roots.x2}")
            is OneRoot -> println("x = ${roots.x}")
            is NoRealRoots -> println("немає")
        }
    }

    println("Всього рівнянь з двома коренями: " + filtered.size)
}