package com.portfolio.domain.piece.move;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.enums.ColorEnum;
import com.portfolio.domain.piece.base.Piece;
import com.portfolio.domain.piece.base.Position;

import java.util.ArrayList;
import java.util.List;

public class PossibleMoves {
    Piece[][] boardPieces;
    int boardSize;
    Position currentPosition;
    ColorEnum color;
    List<Position> moves;

    public PossibleMoves(Board board, Position position, ColorEnum color) {
        this.boardPieces = board.getPieces();
        this.boardSize = board.getSize();
        this.currentPosition = position;
        this.color = color;
        this.moves = new ArrayList<>();
    }

    public void addHorizontalPositions() {
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        // fixed X, add left positions to the list
        int leftSide = y - 1;
        if (leftSide >= 0) {
            for (int boardY = leftSide; boardY >= 0; boardY--) {
                if (currentPieceIsNotNull(x, boardY)) {
                    break;
                }

                moves.add(new Position(x, boardY));
            }
        }

        // fixed X, add right positions to the list
        int rightSide = y + 1;
        if (rightSide < boardSize) {
            for (int boardY = rightSide; boardY < boardSize; boardY++) {
                if (currentPieceIsNotNull(x, boardY)) {
                    break;
                }

                moves.add(new Position(x, boardY));
            }
        }
    }

    public void addVerticalPositions() {
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        // fixed Y, add top positions to the list
        int top = x - 1;
        if (top >= 0) {
            for (int boardX = top; boardX >= 0; boardX--) {
                if (currentPieceIsNotNull(boardX, y)) {
                    break;
                }

                moves.add(new Position(boardX, y));
            }
        }

        // fixed Y, add bottom positions to the list
        int bottom = x + 1;
        if (bottom < boardSize) {
            for (int boardX = bottom; boardX < boardSize; boardX++) {
                if (currentPieceIsNotNull(boardX, y)) {
                    break;
                }

                moves.add(new Position(boardX, y));
            }
        }
    }

    public void addDiagonalPositions() {
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        // Diagonal 1
        int tempX = x - 1;
        int tempY = y - 1;
        while (tempX >= 0 && tempY >= 0) {
            if (currentPieceIsNotNull(tempX, tempY)) {
                break;
            }

            moves.add(new Position(tempX, tempY));
            tempX--;
            tempY--;
        }

        // Diagonal 2
        tempX = x + 1;
        tempY = y + 1;
        while (tempX < boardSize && tempY < boardSize) {
            if (currentPieceIsNotNull(tempX, tempY)) {
                break;
            }

            moves.add(new Position(tempX, tempY));
            tempX++;
            tempY++;
        }

        // Diagonal 3
        tempX = x + 1;
        tempY = y - 1;
        while (tempX < boardSize && tempY >= 0) {
            if (currentPieceIsNotNull(tempX, tempY)) {
                break;
            }

            moves.add(new Position(tempX, tempY));
            tempX++;
            tempY--;
        }

        // Diagonal 4
        tempX = x - 1;
        tempY = y + 1;
        while (tempX >= 0 && tempY < boardSize) {
            if (currentPieceIsNotNull(tempX, tempY)) {
                break;
            }

            moves.add(new Position(tempX, tempY));
            tempX--;
            tempY++;
        }
    }

    public void addLPositions() {
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        int tempX = x + 2;
        int tempY = y + 1;

        tempX = x - 2;
        tempY = y + 1;

        tempX = x + 2;
        tempY = y - 1;

        tempX = x - 2;
        tempY = y - 1;

    }

    private boolean currentPieceIsNotNull(int x, int y) {
        Piece currentPiece = boardPieces[x][y];
        if (currentPiece != null && currentPiece.getColor() == color) {
            return true;
        } else if (currentPiece != null && currentPiece.getColor() != color) {
            moves.add(new Position(x, y));
            return true;
        }

        return false;
    }

    public List<Position> getMoves() {
        return moves;
    }
}
