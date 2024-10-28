# CHIP-8 Emulator in Scala

A CHIP-8 emulator written in Scala, designed to run classic CHIP-8 games and applications. 
This project implements the CHIP-8 architecture, providing an environment for executing and debugging CHIP-8 programs.

## Table of Contents

- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Where to find ROMs](#where-to-find-roms)
- [Potential Enhancements](#potential-enhancements)
- [License](#license)

## Features

- Full implementation of the CHIP-8 instruction set.
- Support for loading and running CHIP-8 ROMs.
- Basic graphical user interface (GUI) for displaying graphics, using PApplet from the Processing framework.
- Added save/load menu within the GUI for saving and restoring game states during the game.

## System Requirements

- **Java Development Kit (JDK)**: Version 8 or higher
- **Gradle**: Version 6.0 or higher
- **Scala**: Version 2.12.19 (managed by Gradle)
- **Processing Core Library**: Automatically managed by Gradle

### Commands to Check Requirements

1. **Java**:
   ```bash
   java -version
   ```
2. **Scala** (if installed separately):
   ```bash
   scala -version
   ```
3. **Gradle**:
   ```bash
   gradle -v
   ```

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/marwandr/CHIP-8-Scala.git
   cd CHIP-8-Scala
   ```

2. Ensure you have all requirements installed.

3. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```

## Usage

1. To run the emulator, execute the following command:
   ```bash
   ./gradlew run
   ```
2. Load a CHIP-8 ROM through the file explorer within the GUI.
3. You can save and load states through the menu within the GUI as well,\
   press "H" on the keyboard during the game to show the save/load menu button.

## Where to Find ROMs

There's a beautiful archive where you can download all kinds of CHIP-8 ROMs on this GitHub: [CHIP-8 Archive](https://johnearnest.github.io/chip8Archive/).

You can also test the emulator with some test ROMs. An interesting test suite you might want to try out can be found here: [CHIP-8 Test Suite](https://github.com/Timendus/chip8-test-suite).


## Potential Enhancements

In the future, several features may be added to enhance the functionality of the emulator. Here are some possibilities:

- **Settings Customizer**: Allow users to configure various emulator settings to suit their preferences.
- **Colors Customizer**: Enable users to change the color scheme of the emulator for a more personalized experience.
- **Debugger Mode**: Implement a debugging interface that displays the stack, currently loaded instructions, registers, and other useful debugging information.

## License

This project is licensed under the Apache License, version 2.0. See the [LICENSE.md](LICENSE.md) file for more details.
