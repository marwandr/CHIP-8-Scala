package chip8
import javax.sound.sampled._

object Sound {
  def beep(frequency: Float, duration: Int): Unit = {
    val sampleRate = 44100
    val bufferSize = sampleRate * duration / 1000
    val buffer = new Array[Byte](bufferSize)
    val amplitude = 0.5 * 127

    for (i <- 0 until bufferSize) {
      buffer(i) =
        (amplitude * Math.sin(2.0 * Math.PI * frequency * (i / sampleRate.toDouble))).toByte
    }

    playSound(buffer, sampleRate)
  }

  private def playSound(buffer: Array[Byte], sampleRate: Int): Unit = {
    val audioFormat = new AudioFormat(sampleRate, 8, 1, true, true)
    val info = new DataLine.Info(classOf[SourceDataLine], audioFormat)
    val line = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]

    try {
      line.open(audioFormat)
      line.start()
      line.write(buffer, 0, buffer.length)
      line.drain()
    } finally {
      line.stop()
      line.close()
    }
  }
}
