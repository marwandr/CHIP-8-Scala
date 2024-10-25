package chip8

case class Settings(
    shifting: Boolean,
    clipping: Boolean,
    reset: Boolean,
    memory: Boolean,
    displayWait: Boolean
)

object Settings {
  def initialize: Settings = Settings(
    shifting = false,
    clipping = true,
    reset = true,
    memory = true,
    displayWait = true
  )
}
