package com.andrecarneiro00.core.piece.base;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
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
