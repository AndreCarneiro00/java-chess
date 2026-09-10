package com.andrecarneiro00.domain.piece;

import com.andrecarneiro00.domain.board.Board;
import com.andrecarneiro00.domain.piece.base.Position;
import com.andrecarneiro00.domain.enums.ColorEnum;
import com.andrecarneiro00.domain.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    Position initialPosition;
    boolean leftEnPassant;
    boolean rightEnPassant;
    public Pawn(ColorEnum color, Position position) {
        super(color, position);
        this.initialPosition = new Position(position.getX(), position.getY());
        this.leftEnPassant = false;
        this.rightEnPassant = false;
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

        if (x + 1 >= boardSize) {
            return moves;
        }

        Piece frontPiece = boardPieces[x + 1][y];
        Piece doubleFrontPiece = boardPieces[x + 2][y];
        if (initialPosition.equals(position) && frontPiece == null && doubleFrontPiece == null) {
            moves.add(new Position(x + 2, y));
        }

        if (frontPiece == null) {
            moves.add(new Position(x + 1, y));
        }

        Piece diagronalLeftPiece = boardPieces[x + 1][y - 1];
        if (diagronalLeftPiece != null && diagronalLeftPiece.getColor() != color) {
            moves.add(new Position(x + 1, y - 1));
        }

        Piece diagonalRightPiece = boardPieces[x + 1][y + 1];
        if (diagonalRightPiece != null && diagonalRightPiece.getColor() != color) {
            moves.add(new Position(x + 1, y + 1));
        }

//        if (leftEnPassant) {
//            moves.add(new Position(x + 1, y + 1));
//        }
//
//        if (rightEnPassant) {
//            moves.add(new Position(x + 1, y + 1));
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
