package com.andrecarneiro00.core.game.board;

public class Move {
    private final Position source;
    private final Position target;
    public Move(Position source, Position target) {
        this.source = source;
        this.target = target;
    }

    public Position getSource() {
        return source;
    }

    public Position getTarget() {
        return target;
    }
}
