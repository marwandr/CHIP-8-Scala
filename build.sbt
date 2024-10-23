name := "Chip8 Emulator"

scalaVersion := "2.12.19"

libraryDependencies += "org.processing" % "core" % "3.3.7"

Compile / mainClass := Some("chip8.Chip8Emulator")
