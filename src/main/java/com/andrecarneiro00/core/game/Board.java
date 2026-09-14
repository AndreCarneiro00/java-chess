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
        for (int x = 0; x < size; x++) {
            sb.append(x);
            sb.append(" ");
            for (int y = 0; y < size; y++) {
                if (pieces[x][y] == null) {
                    sb.append(".");
                } else {
                    sb.append(pieces[x][y]);
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
