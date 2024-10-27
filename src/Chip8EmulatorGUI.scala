package chip8

import processing.core.PApplet
import processing.core.PConstants.{CENTER, LEFT, BASELINE}

import javax.swing.{JFileChooser, SwingUtilities}

class Chip8EmulatorGUI extends PApplet {
  private val buttonWidth = 200
  private val buttonHeight = 60
  val buttonX = 320 - buttonWidth / 2
  val buttonY = 160 - buttonHeight / 2
  private var isRomLoaded = false
  private var romName = ""
  private var romLoadTime: Long = 0L
  private val cooldownDuration: Long = 1000
  
  private var currentScreen = "emulator"
  private var isSaveMode = true
  private var selectedSlot = -1
  
  override def settings(): Unit = {
    size(640, 320) // Window size
  }

  override def setup(): Unit = {
    background(218, 165, 32)
  }

  override def draw(): Unit = {
    background(218, 165, 32)
    currentScreen match {
      case "emulator" => drawEmulatorScreen()
      case "menu" => drawMenuScreen()
      case "confirm" => drawConfirmScreen()
    }
  }

  def drawEmulatorScreen(): Unit = {
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

  def drawConfirmScreen(): Unit = {
    fill(255)
    textAlign(CENTER, CENTER)
    textSize(20)
    text(s"${if (isSaveMode) "Save" else "Load"} slot ${selectedSlot + 1} already occupied. Replace?", width / 2, height / 2 - 30)

    fill(0, 128, 0)
    rect(width / 2 - 70, height / 2 + 20, 60, 30)
    fill(255)
    text("Yes", width / 2 - 40, height / 2 + 35)

    fill(128, 0, 0)
    rect(width / 2 + 10, height / 2 + 20, 60, 30)
    fill(255)
    text("No", width / 2 + 40, height / 2 + 35)
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
        currentScreen = "menu"
        Chip8Emulator.pause = true
      }
    } else if (currentScreen == "menu") {
      if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 50) {
        currentScreen = "emulator"
        Chip8Emulator.pause = false
      }
      else if (mouseX > 240 && mouseX < 400 && mouseY > 60 && mouseY < 100) {
        isSaveMode = !isSaveMode
      } else {
        for (i <- 0 until 10) {
          val buttonYPos = 120 + i * 45
          if (mouseX > 240 && mouseX < 400 && mouseY > buttonYPos && mouseY < buttonYPos + 40) {
            selectedSlot = i
            if (isSaveMode) {
              if (!Chip8Emulator.saveState(i, false)) currentScreen = "confirm"
              else {
                currentScreen = "emulator"
                Chip8Emulator.pause = false
              }
            }
            else {
              val success = Chip8Emulator.loadState(i)
              if (success) {
                currentScreen = "emulator"
                Chip8Emulator.pause = false
              }
            }
          }
        }
      }
    } else if (currentScreen == "confirm") {
      if (mouseX > width / 2 - 70 && mouseX < width / 2 - 10 && mouseY > height / 2 + 20 && mouseY < height / 2 + 50) {
        Chip8Emulator.saveState(selectedSlot, true)
        currentScreen = "emulator"
        Chip8Emulator.pause = false
      } else if (mouseX > width / 2 + 10 && mouseX < width / 2 + 70 && mouseY > height / 2 + 20 && mouseY < height / 2 + 50) {
        currentScreen = "menu"
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
