package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.piece.base.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    int[][] lMoves;
    public Knight(ColorEnum color, Position position) {
        super(color, position);
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
        for (int[] lMove : lMoves) {
            Position newPosition = new Position(position.getX() + lMove[0], position.getY() + lMove[1]);
            int x = newPosition.getX();
            int y = newPosition.getY();

            if (x < 0 || y < 0 || x >= boardSize|| y >= boardSize) {
                continue;
            }

            Piece piece = board.getPieces()[newPosition.getX()][newPosition.getY()];
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
    public List<Position> listPossibleMoves(Board board) {
        return possibleMoves(lMoves, board);
    }

    @Override
    public String toString() {
        return "k";
    }
}
