package com.andrecarneiro00.core.game.board;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.Bishop;
import com.andrecarneiro00.core.piece.King;
import com.andrecarneiro00.core.piece.Knight;
import com.andrecarneiro00.core.piece.Pawn;
import com.andrecarneiro00.core.piece.Queen;
import com.andrecarneiro00.core.piece.Rook;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.piece.base.Position;

public class ClassicChessInitializer implements BoardInitializer {
    public void init(Piece[][] pieces) {
        for (int x = 0; x < pieces.length; x++) {
            ColorEnum color = null;
            if (x == 0 || x == 1) {
                color = ColorEnum.BLACK;
            }

            if (x == 6 || x == 7) {
                color = ColorEnum.WHITE;
            }

            for (int y = 0; y < pieces.length; y++) {
                Position position = new Position(x, y);

                if (x == 1 || x == 6) {
                    pieces[x][y] = new Pawn(color, position);
                } else if (x == 0 || x == 7) {
                    pieces[x][y] = instanciatePiece(color, position);
                } else {
                    pieces[x][y] = null;
                }
            }
        }
    }

    private Piece instanciatePiece(ColorEnum color, Position position) {
        return switch (position.getY()) {
            case 0, 7 -> new Rook(color, position);
            case 1, 6 -> new Knight(color, position);
            case 2, 5 -> new Bishop(color, position);
            case 3 -> new Queen(color, position);
            case 4 -> new King(color, position);
            default -> null;
        };
    }
}
