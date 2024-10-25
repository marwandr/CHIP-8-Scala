
package chip8
import processing.core.PApplet
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Chip8EmulatorGUI extends PApplet {
  def update(): Unit = {
    redraw()
  }

  override def settings(): Unit = {
    size(640, 320) // Window size
  }

  override def draw(): Unit = {
    // Background color for the emulator
    background(218, 165, 32)

    // Draw pixels from the framebuffer
    for (y <- 0 until Chip8Emulator.SCREEN_HEIGHT) {
      for (x <- 0 until Chip8Emulator.SCREEN_WIDTH) {
        if (
          (Chip8Emulator.context.framebuffer(
            y * Chip8Emulator.SCREEN_WIDTH + x
          ) & 1) == 1
        ) {
          // Sprite color
          fill(139, 69, 19)
          noStroke()
          rect(x * 10, y * 10, 10, 10)
        }
      }
    }
  }

  override def keyPressed(): Unit = {
    Chip8Emulator.handleKey(keyCode, true)
  }

  override def keyReleased(): Unit = {
    Chip8Emulator.handleKey(keyCode, false)
  }
}
