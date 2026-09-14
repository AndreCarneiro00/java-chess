package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.piece.base.FirstMoveAware;
import com.andrecarneiro00.core.piece.base.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.List;

public class King extends Piece implements FirstMoveAware {
    private final int[][] directions;
    private boolean hasMoved;
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
        this.hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void markAsMoved() {
        this.hasMoved = true;
    }

    private void addCastling(List<Position> moves) {
        if (hasMoved) {
            return;
        }
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        List<Position> moves = possibleMoves(directions, board, 1);
        addCastling(moves);
        return moves;
    }

    @Override
    public String toString() {
        return "K";
    }
}
