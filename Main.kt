package parking

fun main() {
    val p = Parking()

    while (true) {
        val cmd = readLine()!!.split(' ')
        if (cmd[0] !in listOf("create", "exit") && p.spots.size == 0) {
            println("Sorry, a parking lot has not been created.")
            continue
        }
        when (cmd[0]) {
            "create" -> p.create(cmd[1].toInt())
            "park" -> p.park(cmd[1], cmd[2])
            "leave" -> p.leave(cmd[1].toInt())
            "reg_by_color" -> p.findByColor(cmd[1], "reg")
            "spot_by_color" -> p.findByColor(cmd[1], "spot")
            "spot_by_reg" -> p.spotByReg(cmd[1])
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
                println("${i + 1} ${s.car?.carId} ${s.car?.carColor}")
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
                s.park(i + 1, Car(id, color))
                println("${s.car?.carColor} car parked in spot ${i + 1}.")
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

    fun findByColor(color: String, fld: String) {
        val list = spots.filter { it.car?.carColor?.lowercase() == color.lowercase() }
        if (list.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            val out = if (fld == "reg") list.map { it.car?.carId } else list.map { it.spotNum }
            println(out.joinToString())
        }
    }

    fun spotByReg(regId: String) {
        val list = spots.filter { it.car?.carId?.lowercase() == regId.lowercase() }
        if (list.isEmpty()) {
            println("No cars with registration number $regId were found.")
        } else {
            println(list.map { it.spotNum }.joinToString())
        }
    }
}

class Spot(var free: Boolean = true) {
    var spotNum: Int = 0
    var car: Car? = null

    fun park(num: Int, _car: Car) {
        spotNum = num
        car = _car
        free = false
    }

    fun leave() {
        car = null
        free = true
    }

    fun isFree() = free
}

data class Car(val carId: String, val carColor: String)