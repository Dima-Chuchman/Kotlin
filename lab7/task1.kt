package lab7

interface Refundable {
    fun refund(amount: Double): Boolean
}

interface EReceiptable {
    val receiptEmail: String
    fun sendReceipt() {
        println(" [E-Receipt] Електронний чек надіслано на пошту: $receiptEmail")
    }
}

abstract class PaymentMethod(
    val transactionId: String,
    val amount: Double,
    val currency: String = "UAH"
) {
    init {
        require(amount > 0.0) { "Сума платежу повинна бути більшою за нуль" }
    }

    abstract fun processPayment(): Boolean

    open fun printDetails() {
        println("Транзакція #$transactionId | Сума: $amount $currency")
    }
}

class CreditCardPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val cardNumber: String,
    override val receiptEmail: String
) : PaymentMethod(transactionId, amount, currency), Refundable, EReceiptable {

    override fun processPayment(): Boolean {
        println("Списання $amount $currency з картки (закінчується на ${cardNumber.takeLast(4)})... Успішно!")
        return true
    }

    override fun refund(amount: Double): Boolean {
        println("Повернення $amount $currency на картку ${cardNumber.takeLast(4)} виконано.")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("  └ Метод: Банківська картка (**** ${cardNumber.takeLast(4)})")
    }
}

class CryptoPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val cryptoAddress: String,
    val networkFee: Double
) : PaymentMethod(transactionId, amount, currency), Refundable {

    override fun processPayment(): Boolean {
        println("Відправка $amount $currency (+ комісія $networkFee) на гаманець ${cryptoAddress.take(6)}...${cryptoAddress.takeLast(4)}... Підтверджено!")
        return true
    }

    override fun refund(amount: Double): Boolean {
        if (amount > networkFee) {
            println("Крипто-повернення ${amount - networkFee} $currency (з урахуванням комісії мережі) надіслано.")
            return true
        } else {
            println("Помилка повернення: сума менша за комісію мережі.")
            return false
        }
    }

    override fun printDetails() {
        super.printDetails()
        println("  └ Метод: Криптовалюта (Адреса: ${cryptoAddress.take(6)}...)")
    }
}

class CashOnDeliveryPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val deliveryAddress: String,
    override val receiptEmail: String
) : PaymentMethod(transactionId, amount, currency), EReceiptable {

    override fun processPayment(): Boolean {
        println("Замовлення зареєстровано. Оплата $amount $currency буде здійснена кур'єру за адресою: $deliveryAddress.")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("  └ Метод: Післяплата (Адреса: $deliveryAddress)")
    }

    override fun sendReceipt() {
        println(" [SMS & E-Receipt] Фіскальний чек зареєстровано та продубльовано на $receiptEmail")
    }
}

fun processMassRefund(items: List<PaymentMethod>, refundPercentage: Double) {
    var successfulRefunds = 0
    for (payment in items) {
        if (payment is Refundable) {
            val amountToRefund = payment.amount * (refundPercentage / 100.0)
            if (payment.refund(amountToRefund)) {
                successfulRefunds++
            }
        }
    }
    println("Успішно оброблено повернень: $successfulRefunds з ${items.size} транзакцій.")
}

fun main() {
    val payments: List<PaymentMethod> = listOf(
        CreditCardPayment(
            transactionId = "TXN-001",
            amount = 2500.0,
            cardNumber = "4149499988884321",
            receiptEmail = "client@gmail.com"
        ),
        CryptoPayment(
            transactionId = "TXN-002",
            amount = 10000.0,
            cryptoAddress = "0x71C8F9B29C",
            networkFee = 50.0
        ),
        CashOnDeliveryPayment(
            transactionId = "TXN-003",
            amount = 1200.0,
            deliveryAddress = "м. Львів, Відділення №5",
            receiptEmail = "receiver@lviv.ua"
        )
    )

    println("==================================================")
    println("        ОБРОБКА ПОТОЧНИХ ТРАНЗАКЦІЙ")
    println("==================================================")

    for (payment in payments) {
        payment.printDetails()
        payment.processPayment()
        if (payment is EReceiptable) {
            payment.sendReceipt()
        }
        println("--------------------------------------------------")
    }

    println("\n==================================================")
    println("       МАСОВЕ ПОВЕРНЕННЯ КОШТІВ (50%)")
    println("==================================================")
    processMassRefund(payments, 50.0)
}