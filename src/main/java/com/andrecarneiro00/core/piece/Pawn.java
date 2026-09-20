package com.andrecarneiro00.core.piece;

import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.piece.base.FirstMoveAware;
import com.andrecarneiro00.core.game.board.Position;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece implements FirstMoveAware {
    boolean hasMoved;
    boolean leftEnPassant;
    boolean rightEnPassant;
    public Pawn(ColorEnum color) {
        super(color);
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
        Position position = board.piecePosition(this);
        int row = position.getRow();
        int col = position.getCol();
        int boardSize = board.getSize();

        int doubleFrontRow = this.color == ColorEnum.BLACK ? row + 2 : row - 2;
        int frontRow = this.color == ColorEnum.BLACK ? row + 1 : row - 1;
        if (frontRow >= boardSize || frontRow < 0) {
            return moves;
        }

        Piece frontPiece = board.pieceAt(new Position(frontRow, col));
        if (doubleFrontRow >= 0 && doubleFrontRow < boardSize) {
            Piece doubleFrontPiece = board.pieceAt(new Position(doubleFrontRow, col));
            if (!hasMoved && frontPiece == null && doubleFrontPiece == null) {
                moves.add(new Position(doubleFrontRow, col));
            }
        }

        if (frontPiece == null) {
            moves.add(new Position(frontRow, col));
        }

        if (col - 1 >= 0) {
            Position diagronalLeftPosition = new Position(frontRow, col - 1);
            Piece diagronalLeftPiece = board.pieceAt(diagronalLeftPosition);
            if (diagronalLeftPiece != null && diagronalLeftPiece.getColor() != color) {
                moves.add(diagronalLeftPosition);
            }
        }

        if (col + 1 < boardSize) {
            Position diagonalRightPosition =  new Position(frontRow, col + 1);
            Piece diagonalRightPiece = board.pieceAt(diagonalRightPosition);
            if (diagonalRightPiece != null && diagonalRightPiece.getColor() != color) {
                moves.add(diagonalRightPosition);
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
        Position position = board.piecePosition(this);
        int row = position.getRow();
        int col = position.getCol();
        int boardSize = board.getSize();


        int frontRow = this.color == ColorEnum.BLACK ? row + 1 : row - 1;
        if (frontRow >= boardSize || frontRow < 0) {
            return false;
        }

        boolean attacksLeft =
                col - 1 >= 0
                        && target.equals(new Position(frontRow, col - 1));

        boolean attacksRight =
                col + 1 < boardSize
                        && target.equals(new Position(frontRow, col + 1));

        return attacksLeft || attacksRight;
    }

    @Override
    public String toString() {
        return "P";
    }
}
