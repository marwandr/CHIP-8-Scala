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
  private var romLoadTime: Long = 0L
  private val cooldownDuration: Long = 1000
  
  private var currentScreen = "emulator" // "emulator" or "menu"
  private var isSaveMode = true // toggles between save and load mode
  
  override def settings(): Unit = {
    size(640, 320) // Window size
  }

  override def setup(): Unit = {
    background(218, 165, 32) // Background color for the emulator
  }

  override def draw(): Unit = {
    if (currentScreen == "emulator") {
      drawEmulatorScreen()
    } else if (currentScreen == "menu") {
      drawMenuScreen()
    }
  }

  def drawEmulatorScreen(): Unit = {
    background(218, 165, 32) // Background color for emulator
    if (!isRomLoaded) {
      drawRomLoaderButton()
    } else {
      drawPixelsFromFramebuffer()
      showRomInfo()
      drawMenuToggleButton()
    }
  }

  def drawRomLoaderButton(): Unit = {
    fill(139, 69, 19)
    rect(buttonX, buttonY, buttonWidth, buttonHeight)
    textSize(20)
    fill(0)
    textAlign(CENTER, CENTER)
    text("Load ROM", buttonX + buttonWidth / 2, buttonY + buttonHeight / 2)
  }

  def showRomInfo(): Unit = {
    if (System.currentTimeMillis() - romLoadTime <= cooldownDuration) {
      textSize(10)
      fill(0)
      textAlign(LEFT, BASELINE)
      text(s"Loaded ROM: $romName", 20, 20)
    }
  }

  def drawMenuToggleButton(): Unit = {
    fill(70, 130, 180)
    rect(10, 10, 100, 40)
    fill(255)
    textSize(15)
    textAlign(CENTER, CENTER)
    text("Menu", 60, 30)
  }

  def drawMenuScreen(): Unit = {
    background(169, 169, 169)
    fill(255)
    textAlign(CENTER, CENTER)
    textSize(25)
    text("Menu", width / 2, 30)

    fill(70, 130, 180)
    rect(10, 10, 100, 40)
    fill(255)
    textSize(15)
    textAlign(CENTER, CENTER)
    text("Back", 60, 30)
    
    // Save/Load Toggle
    fill(139, 69, 19)
    rect(240, 60, 160, 40)
    fill(255)
    textSize(15)
    textAlign(CENTER, CENTER)
    text(if (isSaveMode) "Save Mode" else "Load Mode", 320, 80)
    
    // Slot Buttons
    for (i <- 0 until 4) {
      fill(105, 105, 105)
      rect(240, 120 + i * 45, 160, 40)
      fill(255)
      text(s"Slot ${i + 1}", 320, 140 + i * 45)
    }
  }

  override def mousePressed(): Unit = {
    if (currentScreen == "emulator") {
      if (
        mouseX > buttonX && mouseX < buttonX + buttonWidth &&
        mouseY > buttonY && mouseY < buttonY + buttonHeight
      ) {
        openFileChooser()
      }
      else if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 50) {
        currentScreen = "menu" // Switch to menu screen
        Chip8Emulator.pause = true
      }
    } else if (currentScreen == "menu") {
      if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 50) {
        currentScreen = "emulator" // Go back to emulator screen
        Chip8Emulator.pause = false
      }
      else if (mouseX > 240 && mouseX < 400 && mouseY > 60 && mouseY < 100) {
        isSaveMode = !isSaveMode // Toggle save/load mode
      }
      else {
        for (i <- 0 until 10) {
          val buttonYPos = 120 + i * 45
          if (mouseX > 240 && mouseX < 400 && mouseY > buttonYPos && mouseY < buttonYPos + 40) {
            var success = 0
            if (isSaveMode) {
              if !Chip8Emulator.saveState(i, false) {
                Chip8Emulator.saveState(i, true)
              }
            } else {
                if !Chip8Emulator.loadState(i, false) {
                  Chip8Emulator.loadState(i, true)
                }
            }
          }
        }
      }
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
