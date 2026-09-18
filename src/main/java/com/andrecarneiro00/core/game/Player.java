package com.andrecarneiro00.core.game;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final ColorEnum color;
    private final List<Piece> capturedPieces;
    public Player(ColorEnum color) {
        this.color = color;
        this.capturedPieces = new ArrayList<>();
    }

    public ColorEnum getColor() {
        return color;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void capture(Piece piece) {
        if (piece.getColor() == color) {
            return;
        }
        capturedPieces.add(piece);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player compared
                && this.getColor() == compared.getColor();
    }

    @Override
    public String toString() {
        return color.name();
    }
}
