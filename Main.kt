package parking

fun main() {
    val p = Parking()

    while (true) {
        val cmd = readLine()!!.split(' ')
        if (cmd[0] in listOf("park", "leave", "status") && p.spots.size == 0) {
            println("Sorry, a parking lot has not been created.")
            continue
        }
        when (cmd[0]) {
            "create" -> p.create(cmd[1].toInt())
            "park" -> p.park(cmd[1], cmd[2])
            "leave" -> p.leave(cmd[1].toInt())
            "status" -> p.status()
            "exit" -> break
        }
    }
}

class Parking() {
    var spots = emptyList<Spot>()

    fun create(num: Int) {
        spots = List(num) { Spot() }
        println("Created a parking lot with $num spots.")
    }

    fun status() {
        var isEmpty = true
        for (i in spots.indices) {
            val s = spots[i]
            if (!s.isFree()) {
                println("${i + 1} ${s.carId} ${s.carColor}")
                isEmpty = false
            }
        }
        if (isEmpty) {
            println("Parking lot is empty.")
        }
    }

    fun park(id: String, color: String) {
        var isFull = true
        for (i in spots.indices) {
            val s = spots[i]
            if (s.isFree()) {
                s.park(id, color)
                println("${s.carColor} car parked in spot ${i + 1}.")
                isFull = false
                break
            }
        }
        if (isFull) {
            println("Sorry, the parking lot is full.")
        }
    }

    fun leave(k: Int) {
        if (spots[k - 1].isFree()) {
            println("There is no car in spot $k.")
        } else {
            spots[k - 1].leave()
            println("Spot $k is free.")
        }
    }
}

class Spot(var free: Boolean = true,
           var carId: String = "",
           var carColor: String = "") {

    fun park(id: String, color: String) {
        carId = id
        carColor = color
        free = false
    }

    fun leave() {
        carId = ""
        carColor = ""
        free = true
    }

    fun isFree() = free
}