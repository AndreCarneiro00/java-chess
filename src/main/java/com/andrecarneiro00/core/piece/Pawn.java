package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.piece.base.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    boolean hasMoved;
    boolean leftEnPassant;
    boolean rightEnPassant;
    public Pawn(ColorEnum color, Position position) {
        super(color, position);
        this.hasMoved = false;
        this.leftEnPassant = false;
        this.rightEnPassant = false;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean b) {
        this.hasMoved = b;
    }

    @Override
    public List<Position> possibleMoves(int[][] lMoves, Board board) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unsupported method for Pawns");
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        Piece[][] boardPieces = board.getPieces();
        int boardSize = board.getSize();


        int doubleFrontX = this.color == ColorEnum.BLACK ? x + 2 : x - 2;
        int frontX = this.color == ColorEnum.BLACK ? x + 1 : x - 1;
        if (frontX >= boardSize || frontX < 0) {
            return moves;
        }

        Piece frontPiece = boardPieces[frontX][y];
        if (doubleFrontX >= 0 && doubleFrontX < boardSize) {
            Piece doubleFrontPiece = boardPieces[doubleFrontX][y];
            if (!hasMoved && frontPiece == null && doubleFrontPiece == null) {
                moves.add(new Position(doubleFrontX, y));
            }
        }

        if (frontPiece == null) {
            moves.add(new Position(frontX, y));
        }

        if (y - 1 >= 0) {
            Piece diagronalLeftPiece = boardPieces[frontX][y - 1];
            if (diagronalLeftPiece != null && diagronalLeftPiece.getColor() != color) {
                moves.add(new Position(frontX, y - 1));
            }
        }

        if (y + 1 < boardSize) {
            Piece diagonalRightPiece = boardPieces[frontX][y + 1];
            if (diagonalRightPiece != null && diagonalRightPiece.getColor() != color) {
                moves.add(new Position(frontX, y + 1));
            }
        }

//        if (leftEnPassant) {
//            moves.add(new Position(frontX, y + 1));
//        }
//
//        if (rightEnPassant) {
//            moves.add(new Position(frontX, y + 1));
//        }

        return moves;
    }

    public void setLeftEnPassant(boolean b) {
        this.leftEnPassant = b;
    }

    public void setRightEnPassant(boolean b) {
        this.rightEnPassant = b;
    }

    @Override
    public String toString() {
        return "P";
    }
}
