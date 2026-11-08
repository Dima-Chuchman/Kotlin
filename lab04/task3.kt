package lab4

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val inStock: Boolean
)

val products = listOf(
    Product(1, "Google Pixel 9", "Smartphones", 899.0, 4.8, true),
    Product(2, "iPhone 16 Pro", "Smartphones", 1199.0, 4.9, true),
    Product(3, "Galaxy A55", "Smartphones", 399.0, 4.3, false),
    Product(4, "MacBook Air M3", "Laptops", 1299.0, 4.9, true),
    Product(5, "ThinkPad X1 Carbon", "Laptops", 1499.0, 4.6, false),
    Product(6, "Dell XPS 13", "Laptops", 1150.0, 4.4, true),
    Product(7, "Sony WH-1000XM5", "Audio", 349.0, 4.7, true),
    Product(8, "AirPods Pro 2", "Audio", 249.0, 4.8, false),
    Product(9, "Pixel Buds Pro 2", "Audio", 229.0, 4.5, true)
)

fun List<Product>.filterAndTransform(
    predicate: (Product) -> Boolean,
    transform: (Product) -> String
): List<String> {
    val result = mutableListOf<String>()
    for (item in this) {
        if (predicate(item)) {
            result.add(transform(item))
        }
    }
    return result
}

fun main() {
    products
        .filter { it.inStock && it.rating >= 4.7 && it.price < 1000.0 }
        .sortedByDescending { it.rating }
        .map { "Назва: ${it.name} | Рейтинг: ${it.rating} | Ціна: $${it.price}" }
        .forEach { println(it) }

    println()

    val laptop = products.find { it.category == "Laptops" && it.price > 1200.0 }
    println(laptop?.name ?: "Товар не знайдено")

    val hasExpensiveAudio = products.any { it.category == "Audio" && it.price > 300.0 }
    println(hasExpensiveAudio)

    val allSmartphonesGood = products.filter { it.category == "Smartphones" }.all { it.rating > 4.0 }
    println(allSmartphonesGood)

    println()

    val (available, outOfStock) = products.partition { it.inStock }
    println("Товарів в наявності: ${available.size}")
    println("Товарів не на складі: ${outOfStock.size}")

    val groupedByCategory = products.groupBy { it.category }

    groupedByCategory.forEach { (category, list) ->
        val maxPriceProduct = list.maxByOrNull { it.price }
        if (maxPriceProduct != null) {
            println("Категорія $category -> Найдорожчий: ${maxPriceProduct.name} ($${maxPriceProduct.price})")
        }
    }

    println()

    val promoProducts = products.filterAndTransform(predicate = { it.price < 300.0 }) {
        "Акційна ціна на ${it.name}: лише $${it.price}!"
    }
    promoProducts.forEach { println(it) }

    println()

    val totalAvailablePrice = available.fold(0.0) { accumulator, product ->
        accumulator + product.price
    }

    val averagePrice = if (available.isNotEmpty()) totalAvailablePrice / available.size else 0.0

    println("Загальна вартість товарів в наявності: $$totalAvailablePrice")
    println("Середня вартість товарів в наявності: $$averagePrice")
}