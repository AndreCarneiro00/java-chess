package com.andrecarneiro00.ui.javaFX.view;

import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.game.board.Position;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BoardView {
    private static final String POSSIBLE_MOVE_MARKER = "possible-move-marker";
    private GridPane boardGrid;
    private BoardViewBuilder boardBuilder;

    public Region build(
            Consumer<Position> onSquareClicked,
            BiPredicate<Position, Position> onPieceDropped,
            Board board
    ) {
        GridPane grid = new GridPane();
        configureGrid(grid, board.getSize());
        this.boardGrid = grid;
        this.boardBuilder = new BoardViewBuilder(grid, onSquareClicked, onPieceDropped);

        boardBuilder.renderBoard(board);

        StackPane root = new StackPane(grid);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #312e2b;");

        NumberBinding boardSize = Bindings.min(root.widthProperty(), root.heightProperty());
        grid.prefWidthProperty().bind(boardSize);
        grid.prefHeightProperty().bind(boardSize);
        grid.maxWidthProperty().bind(boardSize);
        grid.maxHeightProperty().bind(boardSize);

        return root;
    }

    private void configureGrid(GridPane grid, int boardSize) {
        grid.setMinSize(0, 0);

        double cellPercentage = 100.0 / boardSize;
        for (int index = 0; index < boardSize; index++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(cellPercentage);
            column.setHgrow(Priority.ALWAYS);
            column.setFillWidth(true);
            grid.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(cellPercentage);
            row.setVgrow(Priority.ALWAYS);
            row.setFillHeight(true);
            grid.getRowConstraints().add(row);
        }
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

            Circle marker = new Circle(0, Color.rgb(90, 90, 90, 0.65));
            marker.radiusProperty().bind(square.widthProperty().multiply(0.12));
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
