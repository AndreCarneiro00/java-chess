package com.andrecarneiro00.domain.piece;

import com.andrecarneiro00.domain.board.Board;
import com.andrecarneiro00.domain.piece.base.Position;
import com.andrecarneiro00.domain.enums.ColorEnum;
import com.andrecarneiro00.domain.piece.base.Piece;

import java.util.List;

public class Queen extends Piece {
    int[][] directions;
    public Queen(ColorEnum color, Position position) {
        super(color, position);
        this.directions = new int[][] {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1},
                {1, 1},
                {-1, -1},
                {1, -1},
                {-1, 1},
        };
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        return possibleMoves(directions, board);
    }

    @Override
    public String toString() {
        return "Q";
    }
}
