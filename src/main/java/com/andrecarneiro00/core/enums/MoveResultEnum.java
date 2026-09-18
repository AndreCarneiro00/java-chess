package com.andrecarneiro00.core.enums;

public enum MoveResultEnum {
    MOVED(true, false),
    INVALID_MOVE(false, false),
    CHECKMATE(true, true),
    STALEMATE(true, true);

    private final boolean moveApplied;
    private final boolean gameOver;

    MoveResultEnum(boolean moveApplied, boolean gameOver) {
        this.moveApplied = moveApplied;
        this.gameOver = gameOver;
    }

    public boolean isMoveApplied() {
        return moveApplied;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
