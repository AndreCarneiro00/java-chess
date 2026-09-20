package com.andrecarneiro00.core.game.board;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(Position position) {
        this.row = position.getRow();
        this.col = position.getCol();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isValid(int limit) {
        return row >= 0 && row < limit && col >= 0 && col < limit;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Position compared
            && this.getRow() == compared.getRow()
            && this.getCol() == compared.getCol();
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
