package com.portfolio.domain.piece.base;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.enums.ColorEnum;

import java.util.List;

public abstract class Piece {
    protected ColorEnum color;
    protected Position position;

    public Piece(ColorEnum color, Position position) {
        this.color = color;
        this.position = position;
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
