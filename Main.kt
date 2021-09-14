package parking

fun main() {
    var spots = emptyList<Spot>()
    var isExit = false
    var isCreate = false

    while (!isExit) {
        val cmd = readLine()!!.split(' ')
        val list = listOf("create", "exit")

        if (cmd[0] !in list && !isCreate) {
            println("Sorry, a parking lot has not been created.")
            continue
        }

        when (cmd[0]) {
            "create" -> {
                val num = cmd[1].toInt()
                spots = create(num)
                isCreate = true
                println("Created a parking lot with $num spots.")
            }
            "park" -> parkCar(spots, cmd[1], cmd[2])
            "leave" -> leaveSpot(spots, cmd[1].toInt())
            "reg_by_color" -> findByColor(spots, cmd[1], "reg")
            "spot_by_color" -> findByColor(spots, cmd[1], "spot")
            "spot_by_reg" -> spotByReg(spots, cmd[1])
            "status" -> status(spots)
            "exit" -> isExit = true
        }
    }
}

class Spot(var free: Boolean = true,
           var carNum: String = "",
           var carColor: String = "") {
    var spotNum: Int = 0
    fun park(carNum: String, carColor: String) {
        this.carNum = carNum
        this.carColor = carColor
        free = false
    }

    fun leave() {
        carNum = ""
        carColor = ""
        free = true
    }

    fun isFree() = free
}

fun create(n: Int): List<Spot> {
    return List(n) { Spot() }
}

fun status(park: List<Spot>) {
    var cnt = 0
    for (s in park) {
        if (!s.isFree()) {
            println("${s.spotNum} ${s.carNum} ${s.carColor}")
            cnt++
        }
    }
    if (cnt == 0) {
        println("Parking lot is empty.")
    }
}

fun getFreeSpotNum(park: List<Spot>): Int {
    for (s in park) {
        if (s.isFree()) return park.indexOf(s) + 1
    }
    return 0
}

fun findByColor(park: List<Spot>, color: String, fld: String) {
    val list = park.filter { it.carColor.lowercase() == color.lowercase() }
    if (list.isEmpty()) {
        println("No cars with color $color were found.")
    } else {
        val out = if (fld == "reg") list.map { it.carNum } else list.map { it.spotNum }
        println(out.joinToString())
    }
}

fun spotByReg(park: List<Spot>, num: String) {
    val list = park.filter { it.carNum.lowercase() == num.lowercase() }
    if (list.isEmpty()) {
        println("No cars with registration number $num were found.")
    } else {
        println(list.map { it.spotNum }.joinToString())
    }
}

fun leaveSpot(park: List<Spot>, n: Int)  {
    if (park[n - 1].isFree()) {
        println("There is no car in spot $n.")
    } else {
        park[n - 1].leave()
        println("Spot $n is free.")
    }
}

fun parkCar(park: List<Spot>, reg: String, color: String) {
    val num = getFreeSpotNum(park)
    if (num > 0) {
        val s = park[num - 1]
        s.park(reg, color)
        s.spotNum = num
        println("${s.carColor} car parked in spot $num.")
    } else {
        println("Sorry, the parking lot is full.")
    }
}
