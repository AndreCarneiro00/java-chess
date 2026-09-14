package com.andrecarneiro00.core.game;

import com.andrecarneiro00.core.game.board.BoardInitializer;
import com.andrecarneiro00.core.piece.base.Piece;

public class Board {
    private int size;
    private Piece[][] pieces;

    public Board(int size, BoardInitializer initializer) {
        this.size = size;
        this.pieces = new Piece[size][size];
        initializer.init(pieces);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = 1; i <= size; i ++) {
            sb.append(i - 1);
            sb.append(" ");
        }
        sb.append("\n");
        for (int row = 0; row < size; row++) {
            sb.append(row);
            sb.append(" ");
            for (int col = 0; col < size; col++) {
                if (pieces[row][col] == null) {
                    sb.append(".");
                } else {
                    sb.append(pieces[row][col]);
                }
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int getSize() {
        return size;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        return display();
    }
}
