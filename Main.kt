package parking

fun main() {
    val p = Parking(2)
    p.spots[0].free = false

    val cmd = readLine()!!.split(' ')
    when (cmd[0]) {
        "park" -> p.park(cmd[1], cmd[2])
        "leave" -> p.leave(cmd[1].toInt())
    }
}

class Parking(val num: Int) {
    val spots = List(num) { Spot() }

    fun park(id: String, color: String) {
        for (i in spots.indices) {
            val s = spots[i]
            if (s.isFree()) {
                s.park(id, color)
                println("${s.carColor} car parked in spot ${i + 1}.")
                break
            }
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