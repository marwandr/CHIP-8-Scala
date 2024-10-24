package chip8

import javax.sound.sampled._

object Sound {
  def beep(frequency: Float, duration: Int): Unit = {
    val sampleRate = 44100
    val bufferSize = sampleRate * duration / 1000
    val buffer = new Array[Byte](bufferSize)
    val amplitude = 0.5 * 127

    for (i <- 0 until bufferSize) {
      buffer(i) = (amplitude * Math.sin(
        2.0 * Math.PI * frequency * (i / sampleRate.toDouble)
      )).toByte
    }

    val audioFormat = new AudioFormat(sampleRate, 8, 1, true, true)
    val info = new DataLine.Info(classOf[SourceDataLine], audioFormat)
    val line = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]

    line.open(audioFormat)
    line.start()
    line.write(buffer, 0, buffer.length)
    line.drain()
    line.stop()
    line.close()
  }
}