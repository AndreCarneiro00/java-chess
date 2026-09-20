package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.board.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.List;

public class Queen extends Piece {
    int[][] directions;
    public Queen(ColorEnum color) {
        super(color);
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
    public boolean attacksPosition(Board board, Position target) {
        return attacks(directions, board, target);
    }

    @Override
    public String toString() {
        return "Q";
    }
}
