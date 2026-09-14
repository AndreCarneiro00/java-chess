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
        Piece piece = board.getPieces()[position.getRow()][position.getCol()];
        return piece != null && piece.getColor() == turn.getColor();
    }

    public List<Position> listPossibleMovesByPosition(Position position) {
        if (!position.isValid(board.getSize())) {
            return new ArrayList<>();
        }

        Piece piece = board.getPieces()[position.getRow()][position.getCol()];
        if (piece == null) {
            return new ArrayList<>();
        }

        return piece.listPossibleMoves(board);
    }

    public boolean movePiece(Position current, Position target) {
        Piece[][] pieces = board.getPieces();
        Piece currentPiece = pieces[current.getRow()][current.getCol()];
        if (currentPiece.getColor() != turn.getColor()) {
            return false;
        }

        Piece targetPiece = pieces[target.getRow()][target.getCol()];
        List<Position> possibleMoves = listPossibleMovesByPosition(current);
        if (!possibleMoves.contains(target)) {
            return false;
        }

        pieces[current.getRow()][current.getCol()] = null;
        currentPiece.getPosition().setRow(target.getRow());
        currentPiece.getPosition().setCol(target.getCol());

        if (targetPiece != null) {
            turn.capture(targetPiece);
        }
        pieces[target.getRow()][target.getCol()] = currentPiece;

        if (currentPiece instanceof Pawn pawnPiece && !pawnPiece.getHasMoved()) {
            pawnPiece.setHasMoved(true);
        }
        passTurn();

        return true;
    }

    public static void handleEnPassant(Piece[][] pieces, Piece currentPiece, Piece targetPiece) {
        int targetRow = targetPiece.getPosition().getRow();
        int targetCol = targetPiece.getPosition().getCol();

        if (targetCol - currentPiece.getPosition().getCol() != 2 || !(currentPiece instanceof Pawn currentPawn)) {
            return;
        }

        Piece targetRightNeighbor = pieces[targetRow][targetCol + 1];
        if (validateEnPassant(currentPawn, targetRightNeighbor)) {
            currentPawn.setRightEnPassant(true);
        }
        Piece targetLeftNeighbor = pieces[targetRow][targetCol - 1];
        if (validateEnPassant(currentPawn, targetLeftNeighbor)) {
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
