package com.portfolio.domain.piece;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.piece.base.Position;
import com.portfolio.domain.enums.ColorEnum;
import com.portfolio.domain.piece.base.Piece;
import com.portfolio.domain.piece.move.PossibleMoves;

import java.util.ArrayList;
import java.util.List;

public class Castle extends Piece {
    public Castle(ColorEnum color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        PossibleMoves possibleMoves = new PossibleMoves(board, position, color);
        possibleMoves.addHorizontalPositions();
        possibleMoves.addVerticalPositions();
        return possibleMoves.getMoves();
    }

    @Override
    public String toString() {
        return "T";
    }
}
