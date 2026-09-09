package com.portfolio.domain.piece;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.piece.base.Position;
import com.portfolio.domain.enums.ColorEnum;
import com.portfolio.domain.piece.base.Piece;
import com.portfolio.domain.piece.move.PossibleMoves;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(ColorEnum color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        PossibleMoves possibleMoves = new PossibleMoves(board, position, color);
        possibleMoves.addHorizontalPositions();
        possibleMoves.addVerticalPositions();
        possibleMoves.addDiagonalPositions();
        return possibleMoves.getMoves();
    }

    @Override
    public String toString() {
        return "Q";
    }
}
