package com.andrecarneiro00.ui.javaFX.view;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.piece.base.Position;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.function.Consumer;

public class BoardView {
    private static final String POSSIBLE_MOVE_MARKER = "possible-move-marker";
    private GridPane boardGrid;
    private BoardViewBuilder boardBuilder;

    public Region build(Consumer<Position> onSquareClicked, Board board) {
        GridPane grid = new GridPane();
        this.boardGrid = grid;
        this.boardBuilder = new BoardViewBuilder(grid);

        boardBuilder.renderBoard(board, onSquareClicked);
        return grid;
    }

    public void changePiece(Position current, Position target, Piece piece) {
        if (boardGrid == null) {
            return;
        }

        clearPossibleMoveMarkers();
        boardBuilder.renderChange(current, target, piece);
    }

    public void highlightPossibleMoves(List<Position> moves) {
        clearPossibleMoveMarkers();

        for (Position move : moves) {
            StackPane square = findSquare(move.getRow(), move.getCol());
            if (square == null) {
                continue;
            }

            Circle marker = new Circle(8, Color.rgb(90, 90, 90, 0.65));
            marker.setId(POSSIBLE_MOVE_MARKER);
            marker.setMouseTransparent(true);
            square.getChildren().add(marker);
        }
    }

    private StackPane findSquare(int row, int col) {
        if (boardGrid == null) {
            return null;
        }

        for (Node node : boardGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            int nodeRow = rowIndex == null ? 0 : rowIndex;
            int nodeCol = colIndex == null ? 0 : colIndex;

            if (nodeRow == row && nodeCol == col && node instanceof StackPane square) {
                return square;
            }
        }

        return null;
    }

    public void clearPossibleMoveMarkers() {
        if (boardGrid == null) {
            return;
        }

        for (Node node : boardGrid.getChildren()) {
            if (node instanceof StackPane square) {
                square.getChildren().removeIf(
                        child -> POSSIBLE_MOVE_MARKER.equals(child.getId())
                );
            }
        }
    }
}
