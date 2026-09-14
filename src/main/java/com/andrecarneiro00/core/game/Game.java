package com.andrecarneiro00.core.game;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.board.BoardInitializer;
import com.andrecarneiro00.core.game.board.ClassicChessInitializer;
import com.andrecarneiro00.core.piece.Pawn;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.piece.base.Position;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Board board;
    Player player1;
    Player player2;
    Player turn;

    public Game(int size, BoardInitializer initializer) {
        this.board = new Board(size, initializer);
        this.player1 = new Player(ColorEnum.WHITE);
        this.player2 = new Player(ColorEnum.BLACK);
        this.turn = player1;
    }

    public Board getBoard() {
        return board;
    }

    public Player getTurn() {
        return turn;
    }

    private void passTurn() {
        this.turn = this.turn.equals(player1) ? player2 : player1;
    }

    public boolean validateSelectedPosition(Position position) {
        if (!position.isValid(board.getSize())) {
            return false;
        }
        Piece piece = board.getPieces()[position.getX()][position.getY()];
        return piece != null && piece.getColor() == turn.getColor();
    }

    public List<Position> listPossibleMovesByPosition(Position position) {
        if (!position.isValid(board.getSize())) {
            return new ArrayList<>();
        }

        Piece piece = board.getPieces()[position.getX()][position.getY()];
        if (piece == null) {
            return new ArrayList<>();
        }

        return piece.listPossibleMoves(board);
    }

    public boolean movePiece(Position current, Position next) {
        Piece[][] pieces = board.getPieces();
        Piece currentPiece = pieces[current.getX()][current.getY()];
        if (currentPiece.getColor() != turn.getColor()) {
            return false;
        }

        Piece nextPiece = pieces[next.getX()][next.getY()];
        List<Position> possibleMoves = listPossibleMovesByPosition(current);
        if (!possibleMoves.contains(next)) {
            return false;
        }

        pieces[current.getX()][current.getY()] = null;
        currentPiece.getPosition().setX(next.getX());
        currentPiece.getPosition().setY(next.getY());

        if (nextPiece != null) {
            turn.capture(nextPiece);
        }
        pieces[next.getX()][next.getY()] = currentPiece;

        if (currentPiece instanceof Pawn pawnPiece && !pawnPiece.getHasMoved()) {
            pawnPiece.setHasMoved(true);
        }
        passTurn();

        return true;
    }

    public static  void handleEnPassant(Piece[][] pieces, Piece currentPiece, Piece nextPiece) {
        int nextPieceX = nextPiece.getPosition().getX();
        int nextPieceY = nextPiece.getPosition().getY();

        if (nextPieceY - currentPiece.getPosition().getY() != 2 || !(currentPiece instanceof Pawn currentPawn)) {
            return;
        }

        Piece nextPieceRightNeighbor = pieces[nextPieceX][nextPieceY + 1];
        if (validateEnPassant(currentPawn, nextPieceRightNeighbor)) {
            currentPawn.setRightEnPassant(true);
        }
        Piece nextPieceLeftNeighbor = pieces[nextPieceX][nextPieceY - 1];
        if (validateEnPassant(currentPawn, nextPieceLeftNeighbor)) {
            currentPawn.setRightEnPassant(true);
        }
    }

    public static boolean validateEnPassant(Pawn currentPiece, Piece neighbor) {
        if (neighbor != null) {
            return false;
        }
        if (neighbor.getColor() != currentPiece.getColor()) {
            return true;
        }

        return false;
    }
}
