# Java Chess

Java Chess is a learning-focused chess application built from scratch to strengthen my understanding of object-oriented programming and software design in Java. The project is intentionally being developed in stages: first as a local chess game, then as an online multiplayer application using sockets, and finally as a game with an AI opponent.

> This repository is primarily a coding practice project. Its value is not only the finished game, but also the design decisions, refactoring, tests, and incremental implementation behind it.

## Project objectives

The main goals of this project are to:

- model a real-world domain with object-oriented programming;
- apply encapsulation, inheritance, polymorphism, interfaces, and separation of responsibilities;
- translate chess rules into maintainable domain logic;
- keep the game engine independent from its user interfaces;
- practice automated testing with JUnit;
- explore client-server communication with Java sockets in a future online mode; and
- study game-playing AI by building a chess opponent in a later phase.

## Current features

- Standard 8x8 chessboard setup
- Movement rules for pawns, rooks, knights, bishops, queens, and kings
- Alternating turns and piece capture
- Legal-move validation, including moves that would leave the king in check
- Checkmate and stalemate detection
- Terminal-based interface
- JavaFX graphical interface with piece selection, move highlighting, and drag-and-drop movement
- JUnit tests for the core game and piece behavior

The project is still in development. Special moves and complete end-of-game feedback in the graphical interface are not yet implemented.

## Design overview

The code separates the chess domain from the presentation layer so that the same game logic can support different ways of playing.

```text
src/main/java/com/andrecarneiro00
|-- core
|   |-- game             # Game flow, players, board, moves, and rules
|   |-- piece            # Piece abstractions and movement behavior
|   `-- enums            # Shared game states and colors
`-- ui
    |-- javaFX           # Graphical interface, controller, and board view
    `-- terminal         # Command-line interface
```

Some examples of the design choices are:

- `Piece` provides the shared abstraction used by every chess piece.
- Each concrete piece owns its movement behavior.
- `FirstMoveAware` models state required by pieces whose rules depend on whether they have moved.
- `BoardInitializer` allows board creation to vary independently of the board itself.
- `ChessRule` centralizes rules that depend on the complete board state, such as check, checkmate, and stalemate.
- The terminal and JavaFX interfaces both use the same `Game` domain layer.

## Technology stack

- Java 25
- JavaFX 25
- Maven
- JUnit 6
- JSVG for rendering chess-piece assets

## Running the project

### Prerequisites

- JDK 25
- Maven 3.9 or newer

Clone the repository and verify the project:

```bash
git clone https://github.com/AndreCarneiro00/java-chess.git
cd java-chess
mvn clean test
```

The easiest way to start either interface is from an IDE with Maven support:

- Run `com.andrecarneiro00.JavaFXLauncher` for the graphical application.
- Run `com.andrecarneiro00.TerminalApp` for the terminal application.

In terminal mode, positions use the `row,column` format, with values from `0` to `7`. Enter `end` instead of a position to stop the game.

## Roadmap

- [ ] Implement en passant
- [ ] Implement castling
- [ ] Complete other special-rule coverage, including promotion
- [ ] Update the JavaFX interface to clearly display checkmate, stalemate, and the winner
- [ ] Build a socket-based server and client flow for online matches
- [ ] Add a chess-playing AI opponent
- [ ] Expand automated tests as each rule and game mode is introduced

## AI usage disclosure

The core implementation in this repository was written by me as a deliberate programming exercise. Generative AI was not used to replace the work of designing or implementing the application. When used, it was limited to code review, feedback, repetitive support tasks and documentation.

This distinction is important to the purpose of the project: the goal is to practice writing, reasoning about, testing, and improving the code myself.

## What this project demonstrates

For recruiters and reviewers, this repository is intended to demonstrate:

- practical object-oriented modeling rather than framework-driven development;
- the ability to separate domain logic from presentation concerns;
- iterative development and refactoring as requirements grow;
- automated testing of business rules; and
- a clear progression toward networking and algorithmic problem-solving.
