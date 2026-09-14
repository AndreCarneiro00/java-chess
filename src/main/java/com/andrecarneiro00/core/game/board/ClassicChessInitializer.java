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
        for (int row = 0; row < pieces.length; row++) {
            ColorEnum color = null;
            if (row == 0 || row == 1) {
                color = ColorEnum.BLACK;
            }

            if (row == 6 || row == 7) {
                color = ColorEnum.WHITE;
            }

            for (int col = 0; col < pieces.length; col++) {
                Position position = new Position(row, col);

                if (row == 1 || row == 6) {
                    pieces[row][col] = new Pawn(color, position);
                } else if (row == 0 || row == 7) {
                    pieces[row][col] = instanciatePiece(color, position);
                } else {
                    pieces[row][col] = null;
                }
            }
        }
    }

    private Piece instanciatePiece(ColorEnum color, Position position) {
        return switch (position.getCol()) {
            case 0, 7 -> new Rook(color, position);
            case 1, 6 -> new Knight(color, position);
            case 2, 5 -> new Bishop(color, position);
            case 3 -> new Queen(color, position);
            case 4 -> new King(color, position);
            default -> null;
        };
    }
}
