file://<WORKSPACE>/chip8.scala
### scala.reflect.internal.MissingRequirementError: class scala.Serializable in compiler mirror not found.

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.19
Classpath:

Options:
-Yrangepos -Xplugin-require:semanticdb


action parameters:
uri: file://<WORKSPACE>/chip8.scala
text:
```scala
package chip8

import processing.core.PApplet
import java.nio.file.Files
import java.nio.file.Paths
import scala.concurrent.Future
import scala.util.Try
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Chip8Emulator {
  val SCREEN_WIDTH = 64
  val SCREEN_HEIGHT = 32
  val MEMORY_SIZE = 4096

  val keyStates = Array.fill(16)(false)
  var context: chipContext = new chipContext(
    memory = Array.fill(MEMORY_SIZE)(0),
    registers = Array.fill(16)(0),
    stack = Array.fill(16)(0.toShort),
    I = 0,
    PC = 0x200,
    SP = 0,
    DT = 0,
    ST = 0,
    framebuffer = Array.fill(SCREEN_WIDTH * SCREEN_HEIGHT)(0)
  )

  var running = true
  var soundPlaying = false
  val shifting = false
  val clipping = true
  val reset = true
  val memory = true

  // Load the font set into memory
  for (i <- 0 until FontSet.fontSet.length) {
    context.memory(0x50 + i) = FontSet.fontSet(i)
  }

  def loadRom(filePath: String): Unit = {
    val romBytes = Try(Files.readAllBytes(Paths.get(filePath))) match {
      case scala.util.Success(bytes) if bytes.length + 0x200 <= context.memory.length =>
        System.arraycopy(bytes, 0, context.memory, 0x200, bytes.length)
      case scala.util.Success(_) =>
        println("Error: ROM size exceeds available memory.")
        sys.exit(1)
      case scala.util.Failure(e) =>
        println(s"Error loading ROM: ${e.getMessage}")
        sys.exit(1)
    }
  }

  def executeInstr(): Unit = {
    val instr = (((context.memory(context.PC) & 0xff) << 8) | (context.memory(context.PC + 1) & 0xff) & 0xffff).toShort
    val instrType = (instr & 0xf000) >> 12

    val x = (instr & 0x0f00) >> 8 // Get X register number
    val y = (instr & 0x00f0) >> 4 // Get Y register number
    val n = instr & 0x000f // Get N value

    instrType match {
      case 0x0 =>
        instr match {
          case 0x00e0 => // Clear the screen
            context.framebuffer.indices.foreach(context.framebuffer(_) = 0)
          case 0x00ee => // Return from a subroutine
            context = context.copy(PC = context.stack(context.SP),SP = (context.SP - 1))
          case _ => // No-op for SYS addr
        }
      case 0x1 => // Jump to address NNN
        context = context.copy(PC = (instr & 0x0fff).toShort)
      case 0x2 => // Call subroutine at NNN
        context = context.copy(SP = (context.SP + 1))
        context.stack(context.SP) = context.PC
        context = context.copy(PC = (instr & 0x0fff).toShort)
      case 0x3 => // Skip next instruction if Vx == NN
        if (context.registers(x) == (instr & 0xff)) {
          nextInstruction()
        }
        nextInstruction()
      case 0x4 => // Skip next instruction if Vx != NN
        if (context.registers(x) != (instr & 0xff)) {
          nextInstruction()
        }
        nextInstruction()
      case 0x5 => // Skip next instruction if Vx == Vy
        if (context.registers(x) == context.registers(y)) {
          nextInstruction()
        }
        nextInstruction()

      case 0x6 => // Set V
        context = context.update(x, (instr & 0xff), Register)
        nextInstruction()

      case 0x7 => // Add Vx, NN
        val sum = ((context.registers(x) & 0xff) + (instr & 0xff)) & 0xff
        context = context.update(x, sum, Register)
        nextInstruction()

      case 0x8 => // Handle 0x8XYN instructions
        n match {
          case 0x0 => // Set Vx = Vy
            val vy = context.registers(y)
            context = context.update(x, vy, Register)
            nextInstruction()

          case 0x1 => // Set Vx = Vx OR Vy
            val or = context.registers(x) | context.registers(y)
            context = context.update(x, or, Register)
            if (reset) {
              context = context.update(15, 0, Register)
            }
            nextInstruction()

          case 0x2 => // Set Vx = Vx AND Vy
            val and = context.registers(x) & context.registers(y)
            context = context.update(x, and, Register)
            if (reset) {
              context = context.update(15, 0, Register)
            }
            nextInstruction()

          case 0x3 => // Set Vx = Vx XOR Vy
            val xor = context.registers(x) ^ context.registers(y)
            context = context.update(x, xor, Register)
            if (reset) {
              context = context.update(15, 0, Register)
            }
            nextInstruction()

          case 0x4 => // Set Vx = Vx + Vy, set VF = carry
            val sum =
              ((context.registers(x) & 0xff) + (context.registers(y) & 0xff))
            val flag = if (sum > 0xff) 1 else 0
            context =
              context.update(x, sum & 0xff, Register).update(15, flag, Register)
            nextInstruction()

          case 0x5 => // Set Vx = Vx - Vy, set VF = NOT borrow
            val vx = context.registers(x)
            val vy = context.registers(y)
            val borrow = if (vx >= vy) 1 else 0
            val result = ((vx - vy) & 0xff)
            context =
              context.update(x, result, Register).update(15, borrow, Register)
            nextInstruction()

          case 0x6 => // Set Vx = Vx SHR 1
            val vy = context.registers(y)
            val lsb = vy & 0x1
            val res = (vy >> 1) & 0xff
            context = context.update(x, res, Register).update(15, lsb, Register)
            nextInstruction()

          case 0x7 => // Set Vx = Vy - Vx, set VF = NOT borrow
            val vx = context.registers(x)
            val vy = context.registers(y)
            val borrow = if (vy >= vx) 1 else 0
            val result = (vy - vx) & 0xff
            context =
              context.update(x, result, Register).update(15, borrow, Register)
            nextInstruction()

          case 0xe => // Set Vx = Vx SHL 1
            val (result, msb) = if (shifting) {
              val vx = context.registers(x)
              val msb = (vx >> 7) & 0x1
              val result = (vx << 1) & 0xff
              (result, msb)
            } else {
              val vy = context.registers(y)
              val msb = (vy >> 7) & 0x1
              val result = (vy << 1) & 0xff
              (result, msb)
            }
            context =
              context.update(x, result, Register).update(15, msb, Register)
            nextInstruction()
        }

      case 0x9 => // Skip next instruction if Vx != Vy.
        if ((context.registers(x) & 0xff) != (context.registers(y) & 0xff)) {
          nextInstruction()
        }
        nextInstruction()

      case 0xa => // Set I
        context = context.copy(I = (instr & 0x0fff).toShort)
        nextInstruction()

      case 0xb => // Jump to location nnn + V0.
        val sum = ((instr & 0x0fff) + context.registers(0)).toShort
        context = context.copy(PC = sum)

      case 0xc => // Set Vx = random byte AND kk
        val random = scala.util.Random.nextInt(256)
        context.registers(x) = (random & (instr & 0x00ff))

      case 0xd => // Draw sprite at (Vx, Vy) with a width of 8 pixels and a height of size N
        val posX = context.registers(x) & 0xff
        val posY = context.registers(y) & 0xff
        val height = n
        drawSprite(posX, posY, height)
        nextInstruction()

      case 0xe => //  Skip next instruction if key with the value of Vx is not pressed.
        (instr & 0x00ff) match {
          case 0x9e =>
            if (keyStates(context.registers(x))) {
              nextInstruction()
            }
            nextInstruction()

          case 0xa1 =>
            if (!keyStates(context.registers(x))) {
              nextInstruction()
            }
            nextInstruction()
        }

      case 0xf =>
        (instr & 0x00ff) match {
          case 0x07 => // Set Vx = delay timer value
            context = context.update(x, context.DT, Register)
            nextInstruction()

          case 0x0a => // Wait for a key press, store the value of the key in Vx
            var keyPressed = false
            for (i <- keyStates.indices) {
              if (keyStates(i)) {
                context = context.update(x, i, Register)
                keyPressed = true
              }
            }
            if (!keyPressed) {
              return
            }
            nextInstruction()

          case 0x15 => // Set delay timer = Vx
            context = context.copy(DT = context.registers(x))
            nextInstruction()

          case 0x18 => // Set sound timer = Vx
            context = context.copy(ST = context.registers(x))
            nextInstruction()

          case 0x1e => // Set I = I + Vx
            context =
              context.copy(I = (context.I + context.registers(x)).toShort)
            nextInstruction()

          case 0x29 => // Set I = location of sprite for digit Vx
            context =
              context.copy(I = (0x50 + context.registers(x) * 5).toShort)
            nextInstruction()

          case 0x33 => // Store BCD representation of Vx in memory locations I, I+1, and I+2
            val value = context.registers(x) & 0xff
            context = context
              .update(context.I, (value / 100), Memory)
              .update(context.I + 1, ((value / 10) % 10), Memory)
              .update(context.I + 2, (value % 10), Memory)
            nextInstruction()

          case 0x55 => // Store registers V0 through Vx in memory starting at location I
            val max = Math.min(x, 15)
            for (i <- 0 to max) {
              context = context.update(
                context.I + i,
                context.registers(i) & 0xff,
                Memory
              )
            }
            if (memory) {
              context = context.copy(I = (context.I + x + 1).toShort)
            }
            nextInstruction()

          case 0x65 => // Read registers V0 through Vx from memory starting at location I
            val max = Math.min(x, 15)
            for (i <- 0 to max) {
              context = context.update(
                i,
                context.memory(context.I + i) & 0xff,
                Register
              )
            }
            if (memory) {
              context = context.copy(I = (context.I + x + 1).toShort)
            }
            nextInstruction()
        }
    }
  }

  def drawSprite(x: Int, y: Int, height: Int): Unit = {
    context.registers(15) = 0
    for (row <- 0 until height) {
      val sprite = context.memory(context.I + row)

      for (col <- 0 until 8) {
        val (posX, posY) =
          if (clipping) (x + col, y + row)
          else (x + col % SCREEN_WIDTH, y + row % SCREEN_HEIGHT)
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

  def keyConversion(key: Int): Int = {
    key match {
      case '1' => 1 // CHIP-8 key 1
      case '2' => 2 // CHIP-8 key 2
      case '3' => 3 // CHIP-8 key 3
      case '4' => 12 // CHIP-8 key C
      case 'Q' => 4 // CHIP-8 key 4
      case 'W' => 5 // CHIP-8 key 5
      case 'E' => 6 // CHIP-8 key 6
      case 'R' => 13 // CHIP-8 key D
      case 'A' => 7 // CHIP-8 key 7
      case 'S' => 8 // CHIP-8 key 8
      case 'D' => 9 // CHIP-8 key 9
      case 'F' => 14 // CHIP-8 key E
      case 'Z' => 10 // CHIP-8 key A
      case 'X' => 0 // CHIP-8 key 0
      case 'C' => 11 // CHIP-8 key B
      case 'V' => 15 // CHIP-8 key F
      case _   => println(key); -1 // Return -1 for unrecognized keys
    }
  }

  def handleKeyPress(keyCode: Int): Unit = {
    val key = keyConversion(keyCode)
    println(f"Key pressed: $key")
    if (key == -1) {
      return
    }
    keyStates(key) = true
  }

  def handleKeyRelease(keyCode: Int): Unit = {
    val key = keyConversion(keyCode)
    println(f"Key released: $key")
    if (key == -1) {
      return
    }
    keyStates(key) = false
  }

  def nextInstruction(): Unit = {
    context = context.copy(PC = (context.PC + 2).toShort)
  }

  def updateTimers(): Unit = {
    if (context.DT > 0) {
      context = context.copy(DT = (context.DT - 1))
    }
    if (context.ST > 0) {
      context = context.copy(ST = context.ST - 1)

      if (!soundPlaying) {
        soundPlaying = true
        Future {
          while (context.ST > 0) {
            Sound.beep(440, 100)
          }
          soundPlaying = false
        }
      }
    }
  }

  def mainLoop(): Unit = {
    println("entering main loop")
    val frameDuration = 100000000 / 60 // 60Hz for 60 frames per second
    while (running) {
      val startTime = System.nanoTime()
      executeInstr()
      updateTimers()

      val elapsed = System.nanoTime() - startTime
      val sleepTime = Math.max(0, (frameDuration - elapsed) / 1000000)
      Thread.sleep(sleepTime)
    }
    println("Game exited")
  }

  // Main method (entry point)
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("Please provide a ROM file path.")
      sys.exit(1)
    }

    // Load the ROM
    loadRom(args(0))
    println("ROM loaded")

    val emulatorExecutionContext =
      ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())

    Future {
      mainLoop()
    }(emulatorExecutionContext)

    // Launch the GUI
    PApplet.runSketch(Array("chip8.Chip8EmulatorGUI"), new Chip8EmulatorGUI)
  }
}

```



#### Error stacktrace:

```
scala.reflect.internal.MissingRequirementError$.signal(MissingRequirementError.scala:24)
	scala.reflect.internal.MissingRequirementError$.notFound(MissingRequirementError.scala:25)
	scala.reflect.internal.Mirrors$RootsBase.$anonfun$getModuleOrClass$5(Mirrors.scala:61)
	scala.reflect.internal.Mirrors$RootsBase.getRequiredClass(Mirrors.scala:61)
	scala.reflect.internal.Mirrors$RootsBase.requiredClass(Mirrors.scala:121)
	scala.reflect.internal.Definitions$DefinitionsClass.SerializableClass$lzycompute(Definitions.scala:409)
	scala.reflect.internal.Definitions$DefinitionsClass.SerializableClass(Definitions.scala:409)
	scala.reflect.internal.Definitions$DefinitionsClass.isPossibleSyntheticParent$lzycompute(Definitions.scala:1460)
	scala.reflect.internal.Definitions$DefinitionsClass.isPossibleSyntheticParent(Definitions.scala:1460)
	scala.tools.nsc.typechecker.Typers$Typer.fixDuplicateSyntheticParents(Typers.scala:1703)
	scala.tools.nsc.typechecker.Typers$Typer.typedParentTypes(Typers.scala:1713)
	scala.tools.nsc.typechecker.Namers$Namer.templateSig(Namers.scala:1150)
	scala.tools.nsc.typechecker.Namers$Namer.moduleSig(Namers.scala:1251)
	scala.tools.nsc.typechecker.Namers$Namer.memberSig(Namers.scala:1922)
	scala.tools.nsc.typechecker.Namers$Namer.typeSig(Namers.scala:1870)
	scala.tools.nsc.typechecker.Namers$Namer$MonoTypeCompleter.completeImpl(Namers.scala:877)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete(Namers.scala:2082)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete$(Namers.scala:2080)
	scala.tools.nsc.typechecker.Namers$TypeCompleterBase.complete(Namers.scala:2075)
	scala.reflect.internal.Symbols$Symbol.completeInfo(Symbols.scala:1542)
	scala.reflect.internal.Symbols$Symbol.info(Symbols.scala:1514)
	scala.reflect.internal.Symbols$Symbol.initialize(Symbols.scala:1698)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5442)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5817)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:5881)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$10(Typers.scala:3356)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3356)
	scala.tools.nsc.typechecker.Typers$Typer.typedPackageDef$1(Typers.scala:5449)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5741)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:5817)
	scala.tools.nsc.typechecker.Analyzer$typerFactory$TyperPhase.apply(Analyzer.scala:114)
	scala.tools.nsc.Global$GlobalPhase.applyPhase(Global.scala:465)
	scala.tools.nsc.interactive.Global$TyperRun.$anonfun$applyPhase$1(Global.scala:1341)
	scala.tools.nsc.interactive.Global$TyperRun.applyPhase(Global.scala:1341)
	scala.tools.nsc.interactive.Global$TyperRun.typeCheck(Global.scala:1334)
	scala.tools.nsc.interactive.Global.typeCheck(Global.scala:666)
	scala.meta.internal.pc.WithCompilationUnit.<init>(WithCompilationUnit.scala:22)
	scala.meta.internal.pc.SimpleCollector.<init>(PcCollector.scala:340)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzycompute$1(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:73)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:186)
```
#### Short summary: 

scala.reflect.internal.MissingRequirementError: class scala.Serializable in compiler mirror not found.