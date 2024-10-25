package chip8

sealed trait DataStruct
case object Register extends DataStruct
case object Stack extends DataStruct
case object Memory extends DataStruct

class chipContext(
    val memory: Array[Byte],
    val registers: Array[Integer],
    val stack: Array[Integer],
    val I: Int,
    val PC: Int,
    val SP: Int,
    val DT: Int,
    val ST: Int,
    val framebuffer: Array[Byte],
    val running: Boolean,
    val soundPlaying: Boolean,
    val breakOut: Boolean
) {
  def copy(
      memory: Array[Byte] = this.memory,
      registers: Array[Integer] = this.registers,
      stack: Array[Integer] = this.stack,
      I: Int = this.I,
      PC: Int = this.PC,
      SP: Int = this.SP,
      DT: Int = this.DT,
      ST: Int = this.ST,
      frameBuffer: Array[Byte] = this.framebuffer,
      running: Boolean = this.running,
      soundPlaying: Boolean = this.soundPlaying,
      breakOut: Boolean = this.breakOut
  ): chipContext = {
    new chipContext(
      memory = memory,
      registers = registers,
      stack = stack,
      I = I,
      PC = PC,
      SP = SP,
      DT = DT,
      ST = ST,
      framebuffer = framebuffer,
      running = running,
      soundPlaying = soundPlaying,
      breakOut = breakOut
    )
  }

  def update(index: Int, value: Int, dataStruct: DataStruct): chipContext = {
    dataStruct match {
      case Register =>
        val newRegisters = registers.clone()
        newRegisters(index) = value
        this.copy(registers = newRegisters)

      case Stack =>
        val newStack = stack.clone()
        newStack(index) = value.toShort
        this.copy(stack = newStack)

      case Memory =>
        val newMemory = memory.clone()
        newMemory(index) = (value & 0xff).toByte
        this.copy(memory = newMemory)
    }
  }

  def updateRegisters(updates: Map[Int, Byte]): chipContext = {
    val newRegisters = registers.clone()
    updates.foreach { case (index, value) =>
      newRegisters(index) = value
    }
    this.copy(registers = newRegisters)
  }

  def updateStack(Index: Int, value: Short): chipContext = {
    val newStack = stack.clone()
    newStack(Index) = value
    this.copy(stack = newStack)
  }
}