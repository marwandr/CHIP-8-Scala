package chip8

import processing.core.PApplet

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.file.{Files, Paths}
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.Executors
import scala.util.Try

object Chip8Emulator {
  val SCREEN_WIDTH = 64
  val SCREEN_HEIGHT = 32
  val MEMORY_SIZE = 4096
  val romLoadedPromise = Promise[Unit]()

  val keyStates = Array.fill(16)(false)
  val defaultContext: chipContext = new chipContext(
    memory = Array.fill(MEMORY_SIZE)(0),
    registers = Array.fill(16)(0),
    stack = Array.fill(16)(0.toShort),
    I = 0,
    PC = 0x200,
    SP = 0,
    DT = 0,
    ST = 0,
    framebuffer = Array.fill(SCREEN_WIDTH * SCREEN_HEIGHT)(0),
    running = true,
    soundPlaying = false,
    breakOut = false,
    gameName = ""
  )

  var context = defaultContext

  // Edit the settings file to change the emulator settings
  // such as shifting, clipping, reset, memory, and displayWait
  // currently the settings are set to the default CHIP-8 settings
  var settings = Settings.initialize

  // Pauses the main loop if true
  @volatile var pause = false

  // Load the font set into memory
  for (i <- 0 until FontSet.fontSet.length) {
    context.memory(0x50 + i) = FontSet.fontSet(i)
  }

  def loadRom(filePath: String): Unit = {
    val romBytes = Try(Files.readAllBytes(Paths.get(filePath))) match {
      case scala.util.Success(bytes)
          if bytes.length + 0x200 <= context.memory.length =>
        System.arraycopy(bytes, 0, context.memory, 0x200, bytes.length)
        romLoadedPromise.trySuccess(())
        context = context.copy(gameName = Paths.get(filePath).getFileName.toString)
      case scala.util.Success(_) =>
        println("Error: ROM size exceeds available memory.")
        sys.exit(1)
      case scala.util.Failure(e) =>
        println(s"Error loading ROM: ${e.getMessage}")
        sys.exit(1)
    }
  }

  def executeInstr(): Unit = {
    val instr = (((context.memory(context.PC) & 0xff) << 8) | (context.memory(
      context.PC + 1
    ) & 0xff) & 0xffff).toShort
    val instrType = (instr & 0xf000) >> 12

    val x = (instr & 0x0f00) >> 8 // Get X register number
    val y = (instr & 0x00f0) >> 4 // Get Y register number
    val kk = instr & 0x00ff
    val n = instr & 0x000f

    instrType match {
      case 0x0 =>
        instr match {
          case 0x00e0 => // Clear the screen
            for (i <- 0.until(context.framebuffer.length)) context.framebuffer(i) = 0
          case 0x00ee => // Return from a subroutine
            context = context.copy(
              PC = context.stack(context.SP),
              SP = (context.SP - 1)
              )
          case i if (i & 0xf000) == 0x0000 => // SYS addr
            println("SYS instruction not supported. Instruction: " + instr)
        }
      case 0x1 => // Jump to address NNN
        context = context.copy(PC = (instr & 0x0fff).toShort)
        return
      case 0x2 => // Call subroutine at NNN
        context = context.copy(SP = (context.SP + 1))
        context.stack(context.SP) = context.PC
        context = context.copy(PC = (instr & 0x0fff).toShort)
        return
      case 0x3 | 0x4 | 0x5 => // Skip based on conditions
        val condition = instrType match {
          case 0x3 => context.registers(x) == (instr & 0xff)        // Vx == NN
          case 0x4 => context.registers(x) != (instr & 0xff)        // Vx != NN
          case 0x5 => context.registers(x) == context.registers(y)  // Vx == Vy
        }
        if (condition) nextInstruction()
      case 0x6 | 0x7 =>
        val value = (instr & 0xff)
        val sum = ((context.registers(x) & 0xff) + value) & 0xff
        val operation = instrType match {
          case 0x6 => context = context.update(x, value, Register) // Set Vx = NN
          case 0x7 => context = context.update(x, sum, Register)   // Add NN to Vx
        }
      case 0x8 => // Handle 0x8XYN instructions
        handle8XYN(x, y, n)
      case 0x9 => // Skip next instruction if Vx != Vy.
        if ((context.registers(x) & 0xff) != (context.registers(y) & 0xff)) nextInstruction()
      case 0xa => // Set I
        context = context.copy(I = (instr & 0x0fff).toShort)
      case 0xb => // Jump to location nnn + V0.
        val sum = ((instr & 0x0fff) + context.registers(0)).toShort
        context = context.copy(PC = sum)
        return
      case 0xc => // Set Vx = random byte AND kk
        val random = scala.util.Random.nextInt(256)
        context.registers(x) = (random & kk)
      case 0xd => // Draw sprite at (Vx, Vy) with a width of 8 pixels and a height of size N
        val posX = context.registers(x) & 0xff
        val posY = context.registers(y) & 0xff
        val height = n
        drawSprite(posX % SCREEN_WIDTH, posY % SCREEN_HEIGHT, height)
        context = context.copy(breakOut = true)
      case 0xe =>
        kk match {
          case 0x9e =>
            if (keyStates(context.registers(x))) nextInstruction()  // Skip next instruction if key with the value of Vx is pressed.
          case 0xa1 =>
            if (!keyStates(context.registers(x))) nextInstruction() // Skip next instruction if key with the value of Vx is not pressed.
        }
      case 0xf =>
        handleF(x, y, kk)
    }
    nextInstruction()
  }

  def handle8XYN(x: Int, y: Int, n: Int): Unit = {
    val vx = context.registers(x)
    val vy = context.registers(y)
    def resetFlag(): Unit = if (settings.reset) context = context.update(15, 0, Register)

    n match {
      case 0x0 => // Set Vx = Vy
        context = context.update(x, vy, Register)
      case 0x1 => // Set Vx = Vx OR Vy
        val or = vx| vy
        context = context.update(x, or, Register); resetFlag()
      case 0x2 => // Set Vx = Vx AND Vy
        val and = vx & vy
        context = context.update(x, and, Register); resetFlag()
      case 0x3 => // Set Vx = Vx XOR Vy
        val xor = vx ^ vy
        context = context.update(x, xor, Register); resetFlag()
      case 0x4 => // Set Vx = Vx + Vy, set VF = carry
        val sum = (vx & 0xff) + (vy & 0xff)
        val flag = if (sum > 0xff) 1 else 0
        context = context.update(x, sum & 0xff, Register).update(15, flag, Register)
      case 0x5 => // Set Vx = Vx - Vy, set VF = NOT borrow
        val borrow = if (vx >= vy) 1 else 0
        val result = ((vx - vy) & 0xff)
        context = context.update(x, result, Register).update(15, borrow, Register)
      case 0x6 => // Set Vx = Vx SHR 1, set VF = LSB
        val lsb = vy & 0x1
        val res = (vy >> 1) & 0xff
        context = context.update(x, res, Register).update(15, lsb, Register)
      case 0x7 => // Set Vx = Vy - Vx, set VF = NOT borrow
        val borrow = if (vy >= vx) 1 else 0
        val result = (vy - vx) & 0xff
        context = context.update(x, result, Register).update(15, borrow, Register)
      case 0xe => // Set Vx = Vx (or Vy) SHL 1, set VF = MSB
        val (result, msb) = if (settings.shifting) {
          val msb = (vx >> 7) & 0x1
          val result = (vx << 1) & 0xff
          (result, msb) } else {
          val msb = (vy >> 7) & 0x1
          val result = (vy << 1) & 0xff
          (result, msb)
        }
        context = context.update(x, result, Register).update(15, msb, Register)
    }
  }

  def handleF(x: Int, y: Int, kk: Int): Unit = {
    kk match {
      case 0x07 => // Set Vx = delay timer value
        context = context.update(x, context.DT, Register)
      case 0x0a => // Wait for a key press, store the value of the key in Vx
        val keyPressed = keyStates.indexWhere(identity)
        if (keyPressed >= 0) context = context.update(x, keyPressed, Register) else return
      case 0x15 => // Set delay timer = Vx
        context = context.copy(DT = context.registers(x))
      case 0x18 => // Set sound timer = Vx
        context = context.copy(ST = context.registers(x))
      case 0x1e => // Set I = I + Vx
        context = context.copy(I = (context.I + context.registers(x)).toShort)
      case 0x29 => // Set I = location of sprite for digit Vx
        context = context.copy(I = (0x50 + context.registers(x) * 5).toShort)
      case 0x33 => // Store BCD representation of Vx in memory locations I, I+1, and I+2
        val value = context.registers(x) & 0xff
        context = context
          .update(context.I, (value / 100), Memory)
          .update(context.I + 1, ((value / 10) % 10), Memory)
          .update(context.I + 2, (value % 10), Memory)
      case 0x55 => // Store registers V0 through Vx in memory starting at location I
        (0 to Math.min(x, 15)).foreach(i =>
          context = context.update(context.I + i, context.registers(i) & 0xff, Memory)
          )
        if (settings.memory) context = context.copy(I = (context.I + x + 1).toShort)

      case 0x65 => // Read registers V0 through Vx from memory starting at location I
        (0 to Math.min(x, 15)).foreach(i =>
          context = context.update(i, context.memory(context.I + i) & 0xff, Register)
        )
        if (settings.memory) context = context.copy(I = (context.I + x + 1).toShort)
    }
  }

  def drawSprite(x: Int, y: Int, height: Int): Unit = {
    context.registers(15) = 0
    for (row <- 0 until height) {
      val sprite = context.memory(context.I + row)

      for (col <- 0 until 8) {
        val (posX, posY) =
          if (settings.clipping) (x + col, y + row)
          else ((x + col) % SCREEN_WIDTH, (y + row) % SCREEN_HEIGHT)
        val withinBounds =
          posX < SCREEN_WIDTH && posY < SCREEN_HEIGHT && posX >= 0 && posY >= 0
        val pixelSet = (sprite & (0x80 >> col)) != 0

        if (pixelSet && (withinBounds)) {
          val pixelIndex = posY * SCREEN_WIDTH + posX

          if (context.framebuffer(pixelIndex) == 1) {
            context.registers(15) = 1
          }
          context.framebuffer(pixelIndex) =
            (context.framebuffer(pixelIndex) ^ 1).toByte
        }
      }
    }
  }

  def nextInstruction(): Unit = {
    context = context.copy(PC = (context.PC + 2).toShort)
  }

  def keyConversion(key: Int): Int = {
    key match {
      case '1' =>  1  // CHIP-8 key 1
      case '2' =>  2  // CHIP-8 key 2
      case '3' =>  3  // CHIP-8 key 3
      case '4' => 12  // CHIP-8 key C
      case 'Q' =>  4  // CHIP-8 key 4
      case 'W' =>  5  // CHIP-8 key 5
      case 'E' =>  6  // CHIP-8 key 6
      case 'R' => 13  // CHIP-8 key D
      case 'A' =>  7  // CHIP-8 key 7
      case 'S' =>  8  // CHIP-8 key 8
      case 'D' =>  9  // CHIP-8 key 9
      case 'F' => 14  // CHIP-8 key E
      case 'Z' => 10  // CHIP-8 key A
      case 'X' =>  0  // CHIP-8 key 0
      case 'C' => 11  // CHIP-8 key B
      case 'V' => 15  // CHIP-8 key F
      case _   => -1  // Return -1 for unrecognized keys
    }
  }

  def handleKey(keyCode: Int, Press: Boolean): Unit = {
    val key = keyConversion(keyCode)
    if (key != -1) keyStates(key) = Press
  }

  def createSaveFile(slot: Int): Unit = {
    // Define the directory and the file name
    val saveDir = s"saves/${context.gameName}"
    val saveFileName = s"save$slot"
    
    // Create the directory if it does not exist
    val directoryPath = Paths.get(saveDir)
    if (!Files.exists(directoryPath)) {
      Files.createDirectories(directoryPath)
    }
    
    // Create the save file
    val saveFile = new ObjectOutputStream(new FileOutputStream(s"$saveDir/$saveFileName"))
    saveFile.writeObject(context)
    saveFile.close()
  }

  def saveState(slot: Int, confirm: Boolean): Boolean = {
    val file = new File(s"saves/${context.gameName}/save$slot")
    if (!file.exists() || confirm) {
      createSaveFile(slot)
      true
    }
    else false
  }

  def loadState(slot: Int): Boolean = {
    val file = new File(s"saves/${context.gameName}/save$slot")
    
    if (file.exists()) {
      val saveFile = new ObjectInputStream(new FileInputStream(file))
      try {
        val loadedEmulator = saveFile.readObject().asInstanceOf[chipContext]
        context = loadedEmulator
        true
      } catch {
        case e: Exception =>
          println(s"Error loading state: ${e.getMessage}")
          true
      } finally {
        saveFile.close()
      }
    } else {
      false
    }
  }

  def updateTimers(): Unit = {
    if (context.DT > 0) context = context.copy(DT = (context.DT - 1))

    if (context.ST > 0) {
      context = context.copy(ST = context.ST - 1)

      if (!context.soundPlaying) {
        context = context.copy(soundPlaying = true)
        Future {
          Sound.beep(440, 60)
          context = context.copy(soundPlaying = false)
        }
      }
    }
  }

  def mainLoop(): Unit = {
    val frameDuration = 1000000000 / 60
    val instructionsPerFrame = 16

    while (context.running) {
      while (pause) {
        Thread.sleep(10)
      }

      var i = 0
      val startTime = System.nanoTime()

      while ((!context.breakOut || !settings.displayWait) && i < instructionsPerFrame) {
        executeInstr()
        i += 1
      }
      context = context.copy(breakOut = false)

      updateTimers()
      val elapsed = System.nanoTime() - startTime
      val sleepTime = Math.max(0, (frameDuration - elapsed) / 1000000)
      Thread.sleep(sleepTime)
    }
  }

  // Main method (entry point)
  def main(args: Array[String]): Unit = {
    // Launch the GUI
    PApplet.runSketch(Array("chip8.Chip8EmulatorGUI"), new Chip8EmulatorGUI)

    // Use the Promise to delay main loop until ROM is loaded
    val emulatorExecutionContext = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
    romLoadedPromise.future.foreach { _ =>
      Future {
        mainLoop()
      }(emulatorExecutionContext)
    }
  }
}
