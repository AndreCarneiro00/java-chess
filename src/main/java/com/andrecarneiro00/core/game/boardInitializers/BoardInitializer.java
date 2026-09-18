package com.andrecarneiro00.core.game.boardInitializers;

import com.andrecarneiro00.core.piece.base.Piece;

public interface BoardInitializer {
    void init(Piece[][] pieces);
}
