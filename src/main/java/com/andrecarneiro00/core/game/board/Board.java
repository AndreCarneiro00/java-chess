package com.andrecarneiro00.core.game.board;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.board.initializers.BoardInitializer;
import com.andrecarneiro00.core.piece.King;
import com.andrecarneiro00.core.piece.base.Piece;

public class Board {
    private final int size;
    private final Piece[][] pieces;

    public Board(int size, BoardInitializer initializer) {
        this.size = size;
        this.pieces = new Piece[size][size];
        initializer.init(pieces);
    }

    public Board(Piece[][] piecesToBeCloned) {
        this.size = piecesToBeCloned.length;
        this.pieces = new Piece[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (piecesToBeCloned[i][j] != null) {
                    this.pieces[i][j] = piecesToBeCloned[i][j];
                } else {
                    this.pieces[i][j] = null;
                }
            }
        }

    }

    public Piece pieceAt(Position position) {
        return pieces[position.getRow()][position.getCol()];
    }

    public Position piecePosition(Piece piece) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position position = new Position(row, col);
                if (pieceAt(position) != null && pieceAt(position).equals(piece)) {
                    return position;
                }
            }
        }

        return null;
    }

    private void addPieceByPosition(Position position, Piece piece) {
        pieces[position.getRow()][position.getCol()] = piece;
    }

    private void removePieceByPosition(Position position) {
        pieces[position.getRow()][position.getCol()] = null;
    }

    public boolean movePiece(Move move) {
        Position source = move.getSource();
        Position target = move.getTarget();
        if (!source.isValid(size) || !target.isValid(size)) {
            return false;
        }
        Piece sourcePiece = pieceAt(source);
        removePieceByPosition(source);
        addPieceByPosition(target, sourcePiece);

        return true;
    }

    public King searchKingByColor(ColorEnum color) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Piece piece = pieces[row][col];
                if (piece != null && piece.getColor() != null && piece.getColor() == color && piece instanceof King king) {
                    return king;
                }
            }
        }

        return null;
    }

    public boolean isPositionUnderAttack(Position position, ColorEnum color) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Piece piece = pieces[row][col];
                if (piece != null && piece.getColor() != color && piece.attacksPosition(this, position)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int getSize() {
        return size;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Board project(Move move) {
        Board projectedBoard = new Board(this.getPieces());

        projectedBoard.movePiece(move);
        return projectedBoard;
    }

    @Override
    public String toString() {
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
}
