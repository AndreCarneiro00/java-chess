package com.portfolio.domain.piece;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.piece.base.Position;
import com.portfolio.domain.enums.ColorEnum;
import com.portfolio.domain.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(ColorEnum color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "K";
    }
}
