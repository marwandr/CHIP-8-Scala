# CHIP-8 Emulator in Scala

A CHIP-8 emulator written in Scala, designed to run classic CHIP-8 games and applications. 
This project implements the CHIP-8 architecture, providing an environment for executing and debugging CHIP-8 programs.

## Table of Contents

- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Potential Enhancements](#potential-enhancements)
- [License](#license)

## Features

- Full implementation of the CHIP-8 instruction set.
- Support for loading and running CHIP-8 ROMs.
- Basic graphical user interface (GUI) for displaying graphics, using PApplet from the Processing framework.

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
   ./gradlew run args=path/to/rom.ch8
   ```
2. Load a CHIP-8 ROM by inlcuding its relative file path in the args. Otherwise the emulator won't be able to run.

## Potential Enhancements

In the future, several features may be added to enhance the functionality of the emulator. Here are some possibilities:

- **Settings Customizer**: Allow users to configure various emulator settings to suit their preferences.
- **Colors Customizer**: Enable users to change the color scheme of the emulator for a more personalized experience.
- **Integrated ROM Loader**: Provide a GUI option for loading CHIP-8 ROMs directly within the emulator.
- **Comprehensive Emulator Menu**: Introduce a full menu system to streamline navigation and access to emulator features.
- **Debugger Mode**: Implement a debugging interface that displays the stack, currently loaded instructions, registers, and other useful debugging information.

## License

This project is licensed under the Apache License, version 2.0. See the [LICENSE.md](LICENSE.md) file for more details.
