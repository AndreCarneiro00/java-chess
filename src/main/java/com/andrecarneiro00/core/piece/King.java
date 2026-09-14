package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.piece.base.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.List;

public class King extends Piece {
    int[][] directions;
    public King(ColorEnum color, Position position) {
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
        return possibleMoves(directions, board, 1);
    }

    @Override
    public String toString() {
        return "K";
    }
}
