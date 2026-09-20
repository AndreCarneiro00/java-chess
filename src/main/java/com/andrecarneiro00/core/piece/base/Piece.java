package com.andrecarneiro00.core.piece.base;

import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.board.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected ColorEnum color;

    public Piece(ColorEnum color) {
        this.color = color;
    }

    protected List<Position> possibleMoves(int[][] directions, Board board, Integer limit) {
        List<Position> moves = new ArrayList<>();
        int boardSize = board.getSize();

        for (int[] direction : directions) {
            int rowDirection = direction[0];
            int colDirection = direction[1];

            Position position = board.piecePosition(this);
            int row = position.getRow();
            int col = position.getCol();
            int counter = 0;

            while (true) {
                row = row + rowDirection;
                col = col + colDirection;
                if (row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
                    break;
                }

                if (limit != null && counter == limit) {
                    break;
                }

                Position movePosition = new Position(row, col);
                Piece piece = board.pieceAt(movePosition);
                if (piece != null && piece.getColor() != color) {
                    moves.add(movePosition);
                    break;
                }

                if (piece != null) {
                    break;
                }

                moves.add(movePosition);
                counter++;
            }
        }
        return moves;
    }

    protected boolean attacks(int[][] directions, Board board, Position target, Integer limit) {
        int boardSize = board.getSize();

        for (int[] direction : directions) {
            int rowDirection = direction[0];
            int colDirection = direction[1];

            Position position = board.piecePosition(this);
            int row = position.getRow();
            int col = position.getCol();

            int counter = 0;
            while (true) {
                row = row + rowDirection;
                col = col + colDirection;
                if (row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
                    break;
                }

                if (limit != null && counter == limit) {
                    break;
                }

                Piece piece = board.pieceAt(new Position(row, col));
                if (row == target.getRow() && col == target.getCol()) {
                    return true;
                }

                if (piece != null) {
                    break;
                }

                counter++;
            }
        }
        return false;

    }

    protected List<Position> possibleMoves(int[][] directions, Board board) {
        return this.possibleMoves(directions, board, null);
    }

    protected boolean attacks(int[][] directions, Board board, Position target) {
        return this.attacks(directions, board, target, null);
    }

    abstract public List<Position> listPossibleMoves(Board board);

    abstract public boolean attacksPosition(Board board, Position target);

    public ColorEnum getColor() {
        return color;
    }
}
