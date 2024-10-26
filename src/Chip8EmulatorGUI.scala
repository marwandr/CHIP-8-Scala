package chip8
import processing.core.PApplet
import processing.core.PConstants.BASELINE
import processing.core.PConstants.CENTER
import processing.core.PConstants.LEFT

import javax.swing.JFileChooser
import javax.swing.SwingUtilities

class Chip8EmulatorGUI extends PApplet {
  private val buttonWidth = 200
  private val buttonHeight = 60
  val buttonX = 320 - buttonWidth / 2
  val buttonY = 160 - buttonHeight / 2
  private var isRomLoaded = false
  private var romName = ""
  private var romLoadTime: Long = 0L        // Store ROM load time
  private val cooldownDuration: Long = 1000 // 1 second cooldown

  override def settings(): Unit = {
    size(640, 320) // Window size
  }

  override def setup(): Unit = {
    background(218, 165, 32) // Background color for the emulator
  }

  override def draw(): Unit = {
    // Draw ROM loader button
    if (!isRomLoaded) {
      fill(139, 69, 19)
      rect(buttonX, buttonY, buttonWidth, buttonHeight)
      textSize(20)
      fill(0)
      textAlign(CENTER, CENTER)
      text("Load ROM", buttonX + buttonWidth / 2, buttonY + buttonHeight / 2)
    } else {
      background(218, 165, 32)
      // Draw pixels from the framebuffer
      drawPixelsFromFramebuffer()
      if (System.currentTimeMillis() - romLoadTime <= cooldownDuration) {
        textSize(10)
        fill(0)
        textAlign(LEFT, BASELINE)
        text(s"Loaded ROM: $romName", 20, 20)
      }
    }
  }

  override def mousePressed(): Unit = {
    // Check if the mouse is within the button bounds
    if (
      mouseX > buttonX && mouseX < buttonX + buttonWidth &&
      mouseY > buttonY && mouseY < buttonY + buttonHeight
    ) {
      openFileChooser()
    }
  }

  def openFileChooser(): Unit = {
    SwingUtilities.invokeLater(() => {
      val fileChooser = new JFileChooser()
      val result = fileChooser.showOpenDialog(null)

      if (result == JFileChooser.APPROVE_OPTION) {
        val selectedFile = fileChooser.getSelectedFile
        Chip8Emulator.loadRom(selectedFile.getAbsolutePath)
        romName = selectedFile.getName
        isRomLoaded = true
        romLoadTime = System.currentTimeMillis()
        println(s"Loaded ROM: $romName")
      }
    })
  }

  def drawPixelsFromFramebuffer(): Unit = {
    for (y <- 0 until Chip8Emulator.SCREEN_HEIGHT) {
      for (x <- 0 until Chip8Emulator.SCREEN_WIDTH) {
        if (
          (Chip8Emulator.context.framebuffer(
            y * Chip8Emulator.SCREEN_WIDTH + x
          ) & 1) == 1
        ) {
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