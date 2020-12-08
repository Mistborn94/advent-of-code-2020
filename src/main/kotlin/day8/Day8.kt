package day8


fun solveA(lines: List<String>): Int = Console.of(lines).solveA()
fun solveB(lines: List<String>): Int = Console.of(lines).solveB()

class Console(private val instructions: List<Instruction>) {
    var ip: Int = 0
    var accumulator = 0

    fun solveA(): Int {
        run()
        return accumulator
    }

    fun solveB(): Int {
        val jumps = instructions.filter { it.name == "jmp" }
        val nops = instructions.filter { it.name == "nop" }

        jumps.forEach {
            it.name = "nop"
            if (run() == Status.TERMINATE) {
                return@solveB accumulator
            }
            it.name = "jmp"
        }

        nops.forEach {
            it.name = "jmp"
            if (run() == Status.TERMINATE) {
                return@solveB accumulator
            }
            it.name = "nop"
        }

        throw IllegalStateException("No answer")
    }

    private fun run(): Status {
        accumulator = 0
        ip = 0

        val seen = mutableSetOf<Int>()
        while (ip !in seen && ip in instructions.indices) {
            seen.add(ip)
            val next = instructions[ip]
            next.apply(this)
        }

        return if (ip in instructions.indices) Status.LOOP else Status.TERMINATE
    }

    companion object {
        fun of(lines: List<String>): Console = Console(lines.map(Instruction::of))
    }

    enum class Status {
        LOOP,
        TERMINATE
    }
}

data class Instruction(var name: String, val value: Int) {

    fun apply(console: Console) {
        when (name) {
            "acc" -> {
                console.accumulator += value
                console.ip += 1
            }
            "jmp" -> {
                console.ip += value
            }
            "nop" -> {
                console.ip += 1
            }
        }
    }

    companion object {
        fun of(line: String): Instruction {
            val (key, value) = line.split(" ")
            return Instruction(key, value.toInt())
        }
    }

}

