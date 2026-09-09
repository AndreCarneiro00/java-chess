package com.portfolio.domain.piece.base;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.enums.ColorEnum;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected ColorEnum color;
    protected Position position;

    public Piece(ColorEnum color, Position position) {
        this.color = color;
        this.position = position;
    }

    public List<Position> possibleMoves(int[][] directions, Board board) {
        List<Position> moves = new ArrayList<>();
        int pace = 1;
        int boardSize = board.getSize();

        int x = position.getX();
        int y = position.getY();

        for (int[] direction : directions) {
            int xDirection = direction[0];
            int yDirection = direction[1];
            while (true) {
                x = x + (pace * xDirection);
                y = y + (pace * yDirection);
                if (x < 0 || y < 0 || x >= boardSize|| y >= boardSize) {
                    break;
                }

                Piece piece = board.getPieces()[x][y];
                if (piece != null && piece.getColor() == color) {
                    moves.add(new Position(x, y));
                    break;
                }

                if (piece != null) {
                    break;
                }

                moves.add(new Position(x, y));
            }
        }
        return moves;
    }

    abstract public List<Position> listPossibleMoves(Board board);

    public Position getPosition() {
        return position;
    }

    public ColorEnum getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Piece piece
               && piece.position.equals(this.position);
    }
}
