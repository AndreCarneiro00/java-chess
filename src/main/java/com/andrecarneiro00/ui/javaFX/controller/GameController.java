package com.andrecarneiro00.ui.javaFX.controller;

import com.andrecarneiro00.core.game.Game;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.piece.base.Position;
import com.andrecarneiro00.ui.javaFX.view.BoardView;
import javafx.scene.layout.Region;

import java.util.List;

public class GameController {

    private final Game game;
    private final BoardView boardView;
    private Position selectedPosition;

    public GameController(Game game, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
    }

    public Region initializeView() {
        return boardView.build(this::handleSquareClick, game.getBoard());
    }

    private void handleSquareClick(Position clickedPosition) {
        System.out.println("Casa clicada: Linha " + clickedPosition.getX()
                + ", Coluna " + clickedPosition.getY());

        if (selectedPosition == null) {
            handlePieceSelection(clickedPosition);
        } else {
            handleMoveSelection(clickedPosition);
        }
    }

    private void handlePieceSelection(Position position) {
        if (game.validateSelectedPosition(position)) {
            selectPiece(position);
        } else {
            boardView.clearPossibleMoveMarkers();
        }
    }

    private void handleMoveSelection(Position target) {
        if (game.validateSelectedPosition(target)) {
            selectPiece(target);
            return;
        }

        tryMoveSelectedPiece(target);
    }

    private void selectPiece(Position position) {
        selectedPosition = position;
        List<Position> moves = game.listPossibleMovesByPosition(position);
        boardView.highlightPossibleMoves(moves);
    }

    private void tryMoveSelectedPiece(Position target) {
        Position current = selectedPosition;
        selectedPosition = null;

        if (game.movePiece(current, target)) {
            Piece piece = game.getBoard().getPieces()[target.getX()][target.getY()];
            boardView.changePiece(current, target, piece);
        } else {
            boardView.clearPossibleMoveMarkers();
        }
    }
}
