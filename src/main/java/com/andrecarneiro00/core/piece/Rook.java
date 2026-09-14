package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.piece.base.FirstMoveAware;
import com.andrecarneiro00.core.piece.base.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.List;

public class Rook extends Piece implements FirstMoveAware {
    int[][] directions;
    boolean hasMoved;
    public Rook(ColorEnum color, Position position) {
        super(color, position);
        this.directions = new int[][] {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1},
        };
        this.hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void markAsMoved() {
        this.hasMoved = true;
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        return possibleMoves(directions, board);
    }

    @Override
    public String toString() {
        return "R";
    }
}
