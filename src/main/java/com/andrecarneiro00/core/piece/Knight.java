package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.board.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    int[][] lMoves;
    public Knight(ColorEnum color) {
        super(color);
        this.lMoves = new int[][] {
                {2, 1},
                {2, -1},
                {-2, 1},
                {-2, -1},
                {1, 2},
                {1, -2},
                {-1, 2},
                {-1, -2},
        };
    }

    @Override
    public List<Position> possibleMoves(int[][] lMoves, Board board) {
        List<Position> moves = new ArrayList<>();
        int boardSize = board.getSize();
        Position position = board.piecePosition(this);
        for (int[] lMove : lMoves) {
            Position newPosition = new Position(position.getRow() + lMove[0], position.getCol() + lMove[1]);
            int row = newPosition.getRow();
            int col = newPosition.getCol();

            if (row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
                continue;
            }

            Piece piece = board.pieceAt(newPosition);
            if (piece != null && piece.getColor() != color) {
                moves.add(newPosition);
                continue;
            }

            if (piece != null) {
                continue;
            }

            moves.add(newPosition);
        }
        return moves;
    }

    @Override
    public boolean attacksPosition(Board board, Position target) {
        int boardSize = board.getSize();
        Position position = board.piecePosition(this);
        for (int[] lMove : lMoves) {
            Position newPosition = new Position(position.getRow() + lMove[0], position.getCol() + lMove[1]);
            int row = newPosition.getRow();
            int col = newPosition.getCol();

            if (row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
                continue;
            }

            if (target.equals(newPosition)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        return possibleMoves(lMoves, board);
    }

    @Override
    public String toString() {
        return "k";
    }
}
