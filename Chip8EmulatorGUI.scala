
package chip8
import processing.core.PApplet
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Chip8EmulatorGUI extends PApplet {
  // This method will be called to update the GUI
  def update(): Unit = {
    redraw()
  }

  override def settings(): Unit = {
    size(640, 320) // Set the window size
  }

  override def draw(): Unit = {
    background(0)

    // Draw pixels from the framebuffer
    for (y <- 0 until Chip8Emulator.SCREEN_HEIGHT) {
      for (x <- 0 until Chip8Emulator.SCREEN_WIDTH) {
        if (
          (Chip8Emulator.context.framebuffer(
            y * Chip8Emulator.SCREEN_WIDTH + x
          ) & 1) == 1
        ) {
          fill(255)
          noStroke()
          rect(x * 10, y * 10, 10, 10)
        }
      }
    }
  }

  // Override the keyPressed method to handle key events
  override def keyPressed(): Unit = {
    Chip8Emulator.handleKeyPress(keyCode)
  }

  override def keyReleased(): Unit = {
    Chip8Emulator.handleKeyRelease(keyCode)
  }
}
