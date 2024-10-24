file://<WORKSPACE>/chip8.scala
### java.lang.NullPointerException: Cannot read the array length because "a" is null

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.19
Classpath:
<WORKSPACE>/.bloop/chip8/bloop-bsp-clients-classes/classes-Metals-r2NiZjOkSjCvaj4TvMI0_Q== [missing ], <HOME>/.cache/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.0/semanticdb-javac-0.10.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.19/scala-library-2.12.19.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/processing/core/3.3.7/core-3.3.7.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt-main/2.3.2/gluegen-rt-main-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl/2.3.2/jogl-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all-main/2.3.2/jogl-all-main-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt-main/2.3.2/newt-main-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow-main/2.3.2/nativewindow-main-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-android-aarch64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-android-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-linux-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-linux-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-linux-armv6hf.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-linux-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-macosx-universal.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-solaris-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-solaris-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-windows-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/gluegen/gluegen-rt/2.3.2/gluegen-rt-2.3.2-natives-windows-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-android-aarch64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-android-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-linux-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-linux-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-linux-armv6hf.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-linux-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-macosx-universal.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-solaris-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-solaris-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-windows-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/jogl-all/2.3.2/jogl-all-2.3.2-natives-windows-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-event.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-ogl.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-android.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-bcm-old.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-bcm-vc.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-intelgdl.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-kd.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-linux.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-osx.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-win.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-driver-x11.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-android-aarch64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-android-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-linux-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-linux-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-linux-armv6hf.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-linux-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-macosx-universal.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-solaris-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-solaris-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-windows-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/newt/2.3.2/newt-2.3.2-natives-windows-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-os-win.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-os-osx.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-os-x11.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-android-aarch64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-android-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-linux-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-linux-armv6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-linux-armv6hf.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-linux-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-macosx-universal.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-solaris-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-solaris-i586.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-windows-amd64.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jogamp/jogl/nativewindow/2.3.2/nativewindow-2.3.2-natives-windows-i586.jar [exists ]
Options:
-Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 7482
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
            for (i <- 0.until(context.framebuffer.length)) {
              context.framebuffer(i) = 0
            }
            nextInstruction()
          case 0x00ee => // Return from a subroutine
            context = context.copy(
              PC = context.stack(context.SP),
              SP = (context.SP - 1)
            )
            nextInstruction()
          case i if (i & 0xf000) == 0x0000 => // SYS addr
            nextInstruction()
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
        drawSprite(posX@@, posY, height)
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
java.base/java.util.Arrays.sort(Arrays.java:1233)
	scala.tools.nsc.classpath.JFileDirectoryLookup.listChildren(DirectoryClassPath.scala:119)
	scala.tools.nsc.classpath.JFileDirectoryLookup.listChildren$(DirectoryClassPath.scala:103)
	scala.tools.nsc.classpath.DirectoryClassPath.listChildren(DirectoryClassPath.scala:314)
	scala.tools.nsc.classpath.DirectoryClassPath.listChildren(DirectoryClassPath.scala:314)
	scala.tools.nsc.classpath.DirectoryLookup.list(DirectoryClassPath.scala:84)
	scala.tools.nsc.classpath.DirectoryLookup.list$(DirectoryClassPath.scala:79)
	scala.tools.nsc.classpath.DirectoryClassPath.list(DirectoryClassPath.scala:314)
	scala.tools.nsc.classpath.AggregateClassPath.$anonfun$list$3(AggregateClassPath.scala:105)
	scala.collection.Iterator.foreach(Iterator.scala:943)
	scala.collection.Iterator.foreach$(Iterator.scala:943)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1431)
	scala.collection.IterableLike.foreach(IterableLike.scala:74)
	scala.collection.IterableLike.foreach$(IterableLike.scala:73)
	scala.collection.AbstractIterable.foreach(Iterable.scala:56)
	scala.tools.nsc.classpath.AggregateClassPath.list(AggregateClassPath.scala:101)
	scala.tools.nsc.util.ClassPath.list(ClassPath.scala:36)
	scala.tools.nsc.util.ClassPath.list$(ClassPath.scala:36)
	scala.tools.nsc.classpath.AggregateClassPath.list(AggregateClassPath.scala:30)
	scala.tools.nsc.symtab.SymbolLoaders$PackageLoader.doComplete(SymbolLoaders.scala:298)
	scala.tools.nsc.symtab.SymbolLoaders$SymbolLoader.complete(SymbolLoaders.scala:250)
	scala.reflect.internal.Symbols$Symbol.completeInfo(Symbols.scala:1542)
	scala.reflect.internal.Symbols$Symbol.info(Symbols.scala:1514)
	scala.reflect.internal.Mirrors$RootsBase.init(Mirrors.scala:258)
	scala.tools.nsc.Global.rootMirror$lzycompute(Global.scala:74)
	scala.tools.nsc.Global.rootMirror(Global.scala:72)
	scala.tools.nsc.Global.rootMirror(Global.scala:44)
	scala.reflect.internal.Definitions$DefinitionsClass.ObjectClass$lzycompute(Definitions.scala:301)
	scala.reflect.internal.Definitions$DefinitionsClass.ObjectClass(Definitions.scala:301)
	scala.reflect.internal.Definitions$DefinitionsClass.init(Definitions.scala:1511)
	scala.tools.nsc.Global$Run.<init>(Global.scala:1225)
	scala.tools.nsc.interactive.Global$TyperRun.<init>(Global.scala:1323)
	scala.tools.nsc.interactive.Global.newTyperRun(Global.scala:1346)
	scala.tools.nsc.interactive.Global.<init>(Global.scala:294)
	scala.meta.internal.pc.MetalsGlobal.<init>(MetalsGlobal.scala:44)
	scala.meta.internal.pc.ScalaPresentationCompiler.newCompiler(ScalaPresentationCompiler.scala:522)
```
#### Short summary: 

java.lang.NullPointerException: Cannot read the array length because "a" is null