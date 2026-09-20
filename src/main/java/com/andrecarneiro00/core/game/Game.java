package com.andrecarneiro00.core.game;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.enums.MoveResultEnum;
import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.board.Move;
import com.andrecarneiro00.core.game.board.initializers.BoardInitializer;
import com.andrecarneiro00.core.game.chessRule.ChessRule;
import com.andrecarneiro00.core.piece.Pawn;
import com.andrecarneiro00.core.piece.base.FirstMoveAware;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.game.board.Position;

import java.util.List;

public class Game {
    Board board;
    Player player1;
    Player player2;
    Player turn;
    Player winner;

    public Game(int size, BoardInitializer initializer) {
        this.board = new Board(size, initializer);
        this.player1 = new Player(ColorEnum.WHITE);
        this.player2 = new Player(ColorEnum.BLACK);
        this.turn = player1;
        this.winner = null;
    }

    public Board getBoard() {
        return board;
    }

    public Player getTurn() {
        return turn;
    }

    public Player nextTurn() {
        return this.turn.equals(player1) ? player2 : player1;
    }

    private void passTurn() {
        this.turn = nextTurn();
    }

    private boolean validateCheckMate() {
        boolean checkmate = ChessRule.isCheckmate(board, turn.getColor());
        if (checkmate) {
            this.winner = nextTurn();
            return true;
        }

        return false;
    }

    private boolean validateStalemate() {
        return ChessRule.isStalemate(board, turn.getColor());
    }

    public boolean validateSelectedPosition(Position position) {
        if (!position.isValid(board.getSize())) {
            return false;
        }
        Piece piece = board.pieceAt(position);
        return piece != null && piece.getColor() == turn.getColor();
    }

    public List<Position> listPossibleMovesByPosition(Position position) {
        return ChessRule.listLegalMoves(board, position);
    }

    public MoveResultEnum movePiece(Position current, Position target) {
        Piece currentPiece = board.pieceAt(current);
        if (currentPiece.getColor() != turn.getColor()) {
            return MoveResultEnum.INVALID_MOVE;
        }

        Piece targetPiece = board.pieceAt(target);
        List<Position> possibleMoves = listPossibleMovesByPosition(current);
        if (!possibleMoves.contains(target)) {
            return MoveResultEnum.INVALID_MOVE;
        }

        board.movePiece(new Move(current, target));

        if (targetPiece != null) {
            turn.capture(targetPiece);
        }

        if (currentPiece instanceof FirstMoveAware firstMovePiece && !firstMovePiece.hasMoved()) {
            firstMovePiece.markAsMoved();
        }

        passTurn();

        boolean checkmate = validateCheckMate();

        if (!checkmate) {
            boolean stalemate = validateStalemate();
            return stalemate ? MoveResultEnum.STALEMATE : MoveResultEnum.MOVED;
        }

        return MoveResultEnum.CHECKMATE;
    }

    public static void handleEnPassant(Piece[][] pieces, Piece currentPiece, Piece targetPiece) {
//        int targetRow = targetPiece.getPosition().getRow();
//        int targetCol = targetPiece.getPosition().getCol();
//
//        if (targetCol - currentPiece.getPosition().getCol() != 2 || !(currentPiece instanceof Pawn currentPawn)) {
//            return;
//        }
//
//        Piece targetRightNeighbor = pieces[targetRow][targetCol + 1];
//        if (validateEnPassant(currentPawn, targetRightNeighbor)) {
//            currentPawn.setRightEnPassant(true);
//        }
//        Piece targetLeftNeighbor = pieces[targetRow][targetCol - 1];
//        if (validateEnPassant(currentPawn, targetLeftNeighbor)) {
//            currentPawn.setRightEnPassant(true);
//        }
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
