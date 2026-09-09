package com.portfolio.domain.piece;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.piece.base.Position;
import com.portfolio.domain.enums.ColorEnum;
import com.portfolio.domain.piece.base.Piece;

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
            int x = lMove[0];
            int y = lMove[1];

            if (x < 0 || y < 0 || x >= boardSize|| y >= boardSize) {
                continue;
            }

            Position newPosition = new Position(position.getX() + x, position.getY() + y);
            Piece piece = board.getPieces()[newPosition.getX()][newPosition.getY()];
            if (piece != null && piece.getColor() == color) {
                continue;
            }

            moves.add(newPosition);
        }
        return moves;
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        return possibleMoves(lMoves, board, 1);
    }

    @Override
    public String toString() {
        return "C";
    }
}
