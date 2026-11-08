package lab4

class Employee(val firstName: String, val lastName: String, val position: String) {

    var bonus: Int = 0

    override fun toString(): String {
        return "Employee(firstName=$firstName, lastName=$lastName, position=$position)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (position != other.position) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + position.hashCode()
        return result
    }

    operator fun component1(): String {
        return firstName
    }

    operator fun component2(): String {
        return lastName
    }

    operator fun component3(): String {
        return position
    }

    fun copy(firstName: String = this.firstName, lastName: String = this.lastName, position: String = this.position): Employee {
        return Employee(firstName, lastName, position)
    }
}

fun main() {
    val emp1 = Employee("Іван", "Коваленко", "Програміст")
    val emp2 = Employee("Іван", "Коваленко", "Програміст")
    val emp3 = emp1

    println(emp1)

    println(emp1 === emp2)
    println(emp1 == emp2)

    val (name, surname, pos) = emp1
    println(name)
    println(surname)
    println(pos)

    val emp4 = emp1.copy(position = "Керівник")
    println(emp4)

    val emp5 = Employee("Олена", "Ткач", "Тестувальник")
    emp5.bonus = 500

    val emp6 = Employee("Олена", "Ткач", "Тестувальник")
    emp6.bonus = 1000

    println(emp5 == emp6)
}