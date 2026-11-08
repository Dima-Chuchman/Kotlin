package lab6

fun generateRandomArray(size: Int, maxValue: Int): IntArray? {
    if (size <= 0 || maxValue <= 0) return null
    return IntArray(size) { kotlin.random.Random.nextInt(0, maxValue + 1) }
}

fun main() {
    generateRandomArray(size = 10, maxValue = 50)
        ?.apply {
            for (i in indices) {
                if (this[i] % 2 != 0) {
                    this[i] = this[i] * 2
                } else {
                    this[i] = this[i] / 2
                }
            }
        }
        ?.also { println(it.contentToString()) }
        ?.run { maxOrNull() }
        ?.let { println("Максимальне значення масиву: $it") }
        ?: println("Помилка вхідних даних")
}