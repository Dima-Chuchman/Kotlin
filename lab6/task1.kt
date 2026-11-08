package lab6

data class Address(
    val street: String,
    val city: String?,
    val postalCode: String?
)

data class Client(
    val id: Int,
    val name: String,
    val email: String?,
    val address: Address?,
    val extraData: Any?
)

val clientList = listOf(
    Client(
        id = 1,
        name = "Олена",
        email = "olena@example.com",
        address = Address("вул. Саксаганського, 10", "Київ", "01033"),
        extraData = "VIP-клієнт"
    ),
    Client(
        id = 2,
        name = "Богдан",
        email = null,
        address = Address("вул. Городоцька, 45", "Львів", null),
        extraData = 42
    ),
    Client(
        id = 3,
        name = "Марія",
        email = "maria@example.com",
        address = null,
        extraData = null
    ),
    Client(
        id = 4,
        name = "Дмитро",
        email = null,
        address = Address("вул. Соборна, 1", null, null),
        extraData = "Очікує дзвінка"
    )
)

fun getShippingLabel(client: Client): String {
    val address = client.address ?: return "Самовивіз: Клієнт ${client.name} не надав адреси"
    val city = address.city ?: "Місто не вказано"
    val postalCode = address.postalCode ?: "Індекс невідомий"
    return "Адреса ${client.name}: ${address.street}, $city, $postalCode"
}

fun printClientNote(client: Client) {
    val note = client.extraData as? String ?: "Додаткові примітки відсутні"
    println("Клієнт ${client.name} -> Примітка: $note")
}

fun getClientEmailOrThrow(client: Client): String {
    return client.email ?: throw IllegalArgumentException("Клієнт з ID ${client.id} не має електронної пошти!")
}

fun forceGetPostalCode(client: Client): String {
    return client.address!!.postalCode!!
}

fun main() {
    println("--- Завдання 1 ---")
    println(getShippingLabel(clientList[0]))
    println(getShippingLabel(clientList[1]))
    println(getShippingLabel(clientList[2]))

    println("\n--- Завдання 2 ---")
    clientList.forEach { printClientNote(it) }

    println("\n--- Завдання 3 ---")
    val emails = clientList.map { it.email }.filterNotNull()
    val minLength = emails.minByOrNull { it.length }?.length

    println("База email для розсилки: $emails")
    println("Довжина найкоротшого email: $minLength")

    println("\n--- Завдання 4 ---")
    try {
        getClientEmailOrThrow(clientList[1])
    } catch (e: IllegalArgumentException) {
        println("Перехоплено виняток: ${e.message}")
    }

    println("\n--- Завдання 5 ---")
    println("Індекс клієнта Олена: ${forceGetPostalCode(clientList[0])}")
    try {
        forceGetPostalCode(clientList[1])
    } catch (e: NullPointerException) {
        println("Перехоплено очікуваний NPE: ${e.message}")
    }
}