package com.andrecarneiro00.ui.terminal;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.game.Game;
import com.andrecarneiro00.core.game.Player;
import com.andrecarneiro00.core.piece.base.Position;

import java.util.List;
import java.util.Scanner;

public class TerminalUI{
    private Scanner scanner;

    public TerminalUI() {
        this.scanner = new Scanner(System.in);
    }

    public void start(Game game) {
        System.out.println("\nInstructions: chose the piece you want to move and then the position to move. Always use X,Y format.\n");
        while (true) {
            Board board = game.getBoard();
            Player turn = game.getTurn();
            System.out.println(board);
            System.out.println(turn.getColor() + "'S TURN!");

            System.out.println("Choose a piece to move: ");
            Position piecePosition = promptPosition(board);
            if (piecePosition.getX() == -1) {
                break;
            }

            if (!game.validateSelectedPosition(piecePosition)) {
                System.out.println("Try again!\n");
                continue;
            }

            List<Position> validPositions = game.listPossibleMovesByPosition(piecePosition);
            System.out.println("Choose a valid Position (" + validPositions + ") to that piece: ");
            Position movePosition = promptPosition(board);
            if (movePosition.getX() == -1) {
                break;
            }

            boolean moved = game.movePiece(piecePosition, movePosition);
            if (!moved) {
                System.out.println("Try again!\n");
            }
        }
    }

    public Position promptPosition(Board board) {

        while (true) {
            String position = scanner.nextLine();

            if (position.equals("end")) {
                System.out.println("Ended by user!");
                return new Position(-1, -1);
            }

            int intX;
            int intY;
            String[] coordinates;
            String x;
            String y;
            try {
                coordinates = position.split(",");
                x = coordinates[0].trim();
                y = coordinates[1].trim();

                intX = Integer.parseInt(coordinates[0].trim());
                intY = Integer.parseInt(coordinates[1].trim());
            } catch (Exception e) {
                System.out.println("Invalid input, try again!");
                continue;
            }

            if (
                    coordinates.length == 2
                            && x.matches("\\d+")
                            && y.matches("\\d+")
                            && intX >= 0 && intY>= 0 && intX < board.getSize() && intY < board.getSize()
            ) {
                return new Position(intX, intY);
            }

            System.out.println("Try again!");
        }
    }
}
