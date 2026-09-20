package com.andrecarneiro00.ui.terminal;

import com.andrecarneiro00.core.enums.MoveResultEnum;
import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.Game;
import com.andrecarneiro00.core.game.Player;
import com.andrecarneiro00.core.game.board.Position;

import java.util.List;
import java.util.Scanner;

public class TerminalUI{
    private Scanner scanner;

    public TerminalUI() {
        this.scanner = new Scanner(System.in);
    }

    public void start(Game game) {
        System.out.println("\nInstructions: chose the piece you want to move and then the position to move. Always use ROW,COL format.\n");
        while (true) {
            Board board = game.getBoard();
            Player turn = game.getTurn();
            System.out.println(board);
            System.out.println(turn.getColor() + "'S TURN!");

            System.out.println("Choose a piece to move: ");
            Position current = promptPosition(board);
            if (current.getRow() == -1) {
                break;
            }

            if (!game.validateSelectedPosition(current)) {
                System.out.println("Try again!\n");
                continue;
            }

            List<Position> validPositions = game.listPossibleMovesByPosition(current);
            System.out.println("Choose a valid Position (" + validPositions + ") to that piece: ");
            Position target = promptPosition(board);
            if (target.getRow() == -1) {
                break;
            }

            MoveResultEnum result = game.movePiece(current, target);
            if (result == MoveResultEnum.INVALID_MOVE) {
                System.out.println("Try again!\n");
                continue;
            }

            if (result.isGameOver()) {
                System.out.println(game.getBoard());
                System.out.println(result == MoveResultEnum.CHECKMATE ? "Checkmate!" : "Stalemate!");
                break;
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

            int row;
            int col;
            String[] coordinates;
            String rowText;
            String colText;
            try {
                coordinates = position.split(",");
                rowText = coordinates[0].trim();
                colText = coordinates[1].trim();

                row = Integer.parseInt(rowText);
                col = Integer.parseInt(colText);
            } catch (Exception e) {
                System.out.println("Invalid input, try again!");
                continue;
            }

            if (
                    coordinates.length == 2
                            && rowText.matches("\\d+")
                            && colText.matches("\\d+")
                            && row >= 0 && col >= 0 && row < board.getSize() && col < board.getSize()
            ) {
                return new Position(row, col);
            }

            System.out.println("Try again!");
        }
    }
}
