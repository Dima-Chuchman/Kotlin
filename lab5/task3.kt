package lab5

data class OrderItem(
    val name: String,
    val price: Double,
    val quantity: Int
)

class Order(val id: String) {
    var customerName: String = ""
    var deliveryAddress: String = ""
    var discountPercent: Int = 0
    val items: MutableList<OrderItem> = mutableListOf()

    fun addItem(item: OrderItem) {
        items.add(item)
    }
}

fun main() {
    // Завдання 1. Конфігурація та аудит замовлення (apply + also)
    val order = Order("ORD-2026-001").apply {
        customerName = "Олексій Коваленко"
        deliveryAddress = "вул. Хрещатик, 22, Київ"
        discountPercent = 10

        addItem(OrderItem("Клавіатура", 2500.0, 1))
        addItem(OrderItem("Мишка", 1200.0, 1))
        addItem(OrderItem("Килимок", 400.0, 2))
    }.also {
        println("[LOG] Замовлення ${it.id} успішно налаштовано. Кількість позицій: ${it.items.size}")
    }

    // Завдання 2. Розрахунок підсумкової вартості (run)
    val finalPrice: Double = order.run {
        val basePrice = items.sumOf { it.price * it.quantity }
        basePrice - (basePrice * discountPercent / 100.0)
    }
    println("Розрахована сума: $finalPrice грн")

    // Завдання 3. Обмеження області видимості та трансформація (let)
    val paymentPayload = finalPrice.let {
        val cents = (it * 100).toLong()
        "TXN-${order.id}_AMOUNT_${cents}_UAH"
    }
    println("Транзакційний payload: $paymentPayload")

    // Завдання 4. Форматування та друк товарного чека (with)
    with(order) {
        println("========================================")
        println("ЧЕК ЗАМОВЛЕННЯ: $id")
        println("Отримувач: $customerName")
        println("Адреса доставки: $deliveryAddress")
        println("----------------------------------------")
        println("Товари:")
        items.forEach {
            println(" • ${it.name} (${it.quantity} шт.) - ${it.price * it.quantity} грн")
        }
        println("----------------------------------------")
        println("Знижка клієнта: $discountPercent%")
        println("Разом до сплати: $finalPrice грн")
        println("========================================")
    }
}