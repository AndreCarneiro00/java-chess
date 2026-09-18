package com.andrecarneiro00.core.game;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.enums.MoveResultEnum;
import com.andrecarneiro00.core.game.boardInitializers.BoardInitializer;
import com.andrecarneiro00.core.piece.King;
import com.andrecarneiro00.core.piece.Pawn;
import com.andrecarneiro00.core.piece.base.FirstMoveAware;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.piece.base.Position;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Board board;
    Player player1;
    King whiteKing;
    Player player2;
    King blackKing;
    Player turn;
    Player winner;

    public Game(int size, BoardInitializer initializer) {
        this.board = new Board(size, initializer);
        this.player1 = new Player(ColorEnum.WHITE);
        this.whiteKing = board.searchKingByColor(ColorEnum.WHITE);
        this.player2 = new Player(ColorEnum.BLACK);
        this.blackKing = board.searchKingByColor(ColorEnum.BLACK);
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

    public boolean isPlayerWithoutMovement(ColorEnum turnColor) {
        int size = board.getSize();
        Piece[][] pieces = board.getPieces();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Piece piece = pieces[row][col];
                if (piece != null && piece.getColor() == turnColor && !listPossibleMovesByPosition(piece.getPosition()).isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validateCheckMate() {
        ColorEnum color = turn.getColor();
        Position kingPosition = getKingByColor(color).getPosition();
        if (
                board.isPositionUnderAttack(kingPosition, color)
                && isPlayerWithoutMovement(color)
        ) {
            this.winner = nextTurn();
            System.out.println(winner);
            return true;
        }

        return false;
    }

    private boolean validateStalemate() {
        ColorEnum color = turn.getColor();
        if (isPlayerWithoutMovement(color)) {
            System.out.println(winner);
            return true;
        }

        return false;
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

        List<Position> possibleMoves = piece.listPossibleMoves(board);

        Position kingPosition = getKingByColor(piece.getColor()).getPosition();
        if (!kingPosition.equals(position)) {
            possibleMoves = possibleMoves.stream()
                    .filter(
                            (target) -> !board.project(position, target)
                                        .isPositionUnderAttack(kingPosition, piece.getColor())
                    ).toList();
        } else {
            possibleMoves = possibleMoves.stream()
                    .filter(
                            (target) -> {
                                Position kPosition = new Position(kingPosition);
                                kPosition.setRow(target.getRow());
                                kPosition.setCol(target.getCol());
                                return !board.project(position, target)
                                        .isPositionUnderAttack(kPosition, piece.getColor());
                            }
                    ).toList();
        }

        return possibleMoves;
    }

    public MoveResultEnum movePiece(Position current, Position target) {
        Piece[][] pieces = board.getPieces();
        Piece currentPiece = pieces[current.getRow()][current.getCol()];
        if (currentPiece.getColor() != turn.getColor()) {
            return MoveResultEnum.INVALID_MOVE;
        }

        Piece targetPiece = pieces[target.getRow()][target.getCol()];
        List<Position> possibleMoves = listPossibleMovesByPosition(current);
        if (!possibleMoves.contains(target)) {
            return MoveResultEnum.INVALID_MOVE;
        }

        if (kingWillBeUnderAttackByColor(current, target, turn.getColor())) {
            return MoveResultEnum.INVALID_MOVE;
        }

        pieces[current.getRow()][current.getCol()] = null;
        currentPiece.getPosition().setRow(target.getRow());
        currentPiece.getPosition().setCol(target.getCol());

        if (targetPiece != null) {
            turn.capture(targetPiece);
        }
        pieces[target.getRow()][target.getCol()] = currentPiece;

        if (currentPiece instanceof FirstMoveAware firstMovePiece && !firstMovePiece.hasMoved()) {
            firstMovePiece.markAsMoved();
        }

        if (currentPiece instanceof King king) {
            if (king.getColor() == ColorEnum.BLACK) {
                this.blackKing = king;
            } else {
                this.whiteKing = king;
            }
        }

        passTurn();

        boolean checkmate = validateCheckMate();

        if (!checkmate) {
            boolean stalemate = validateStalemate();
            return stalemate ? MoveResultEnum.STALEMATE : MoveResultEnum.MOVED;
        }

        return MoveResultEnum.CHECKMATE;
    }

    private King getKingByColor(ColorEnum color) {
        if (color == ColorEnum.BLACK) {
            return blackKing;
        } else {
            return whiteKing;
        }
    }

    private boolean isKingUnderAttackByColor(ColorEnum color) {
        Position kingPosition = getKingByColor(color).getPosition();
        if (board.isPositionUnderAttack(kingPosition, color)) {
            return true;
        }
        return false;
    }

    private boolean kingWillBeUnderAttackByColor(Position current, Position target, ColorEnum color) {
        Position kingPosition = getKingByColor(color).getPosition();
        if (kingPosition.equals(current)) {
            kingPosition = new Position(target);
        }

        return board.project(current, target).isPositionUnderAttack(kingPosition, color);
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
