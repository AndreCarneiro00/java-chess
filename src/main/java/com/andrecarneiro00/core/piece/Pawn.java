package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.piece.base.FirstMoveAware;
import com.andrecarneiro00.core.piece.base.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece implements FirstMoveAware {
    boolean hasMoved;
    boolean leftEnPassant;
    boolean rightEnPassant;
    public Pawn(ColorEnum color, Position position) {
        super(color, position);
        this.hasMoved = false;
        this.leftEnPassant = false;
        this.rightEnPassant = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void markAsMoved() {
        this.hasMoved = true;
    }

    @Override
    public List<Position> possibleMoves(int[][] lMoves, Board board) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unsupported method for Pawns");
    }

    @Override
    public List<Position> listPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();
        Piece[][] boardPieces = board.getPieces();
        int boardSize = board.getSize();

        int doubleFrontRow = this.color == ColorEnum.BLACK ? row + 2 : row - 2;
        int frontRow = this.color == ColorEnum.BLACK ? row + 1 : row - 1;
        if (frontRow >= boardSize || frontRow < 0) {
            return moves;
        }

        Piece frontPiece = boardPieces[frontRow][col];
        if (doubleFrontRow >= 0 && doubleFrontRow < boardSize) {
            Piece doubleFrontPiece = boardPieces[doubleFrontRow][col];
            if (!hasMoved && frontPiece == null && doubleFrontPiece == null) {
                moves.add(new Position(doubleFrontRow, col));
            }
        }

        if (frontPiece == null) {
            moves.add(new Position(frontRow, col));
        }

        if (col - 1 >= 0) {
            Piece diagronalLeftPiece = boardPieces[frontRow][col - 1];
            if (diagronalLeftPiece != null && diagronalLeftPiece.getColor() != color) {
                moves.add(new Position(frontRow, col - 1));
            }
        }

        if (col + 1 < boardSize) {
            Piece diagonalRightPiece = boardPieces[frontRow][col + 1];
            if (diagonalRightPiece != null && diagonalRightPiece.getColor() != color) {
                moves.add(new Position(frontRow, col + 1));
            }
        }

//        if (leftEnPassant) {
//            moves.add(new Position(frontRow, col + 1));
//        }
//
//        if (rightEnPassant) {
//            moves.add(new Position(frontRow, col + 1));
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
    public boolean attacksPosition(Board board, Position target) {
        int row = position.getRow();
        int col = position.getCol();
        Piece[][] boardPieces = board.getPieces();
        int boardSize = board.getSize();


        int frontRow = this.color == ColorEnum.BLACK ? row + 1 : row - 1;
        if (frontRow >= boardSize || frontRow < 0) {
            return false;
        }

        if (col - 1 >= 0) {
            Piece diagronalLeftPiece = boardPieces[frontRow][col - 1];
            return diagronalLeftPiece != null && diagronalLeftPiece.getColor() != color && diagronalLeftPiece.getPosition().equals(target);
        }

        if (col + 1 < boardSize) {
            Piece diagonalRightPiece = boardPieces[frontRow][col + 1];
            return diagonalRightPiece != null && diagonalRightPiece.getColor() != color && diagonalRightPiece.getPosition().equals(target);
        }

        return false;
    }

    @Override
    public Pawn deepClone() {
        Pawn clone = new Pawn(color, new Position(this.position));
        if (this.hasMoved) {
            clone.markAsMoved();
        }
        return clone;
    }

    @Override
    public String toString() {
        return "P";
    }
}
