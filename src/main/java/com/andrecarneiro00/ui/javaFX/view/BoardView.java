package com.andrecarneiro00.ui.javaFX.view;

import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.piece.Bishop;
import com.andrecarneiro00.core.piece.King;
import com.andrecarneiro00.core.piece.Knight;
import com.andrecarneiro00.core.piece.Pawn;
import com.andrecarneiro00.core.piece.Queen;
import com.andrecarneiro00.core.piece.Rook;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.piece.base.Position;
import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.SVGLoader;
import com.github.weisj.jsvg.ui.jfx.FXSVGCanvas;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BoardView {
    private static final String POSSIBLE_MOVE_MARKER = "possible-move-marker";
    private static final String PIECE_ASSET_PATH = "/com/andrecarneiro00/ui/javaFX/assets/";
    private static final double PIECE_IMAGE_SIZE = 45;
    private static final Map<Class<? extends Piece>, Map<ColorEnum, String>> PIECE_ASSETS = Map.of(
            King.class, Map.of(
                    ColorEnum.WHITE, "whiteKing.svg",
                    ColorEnum.BLACK, "darkKing.svg"
            ),
            Queen.class, Map.of(
                    ColorEnum.WHITE, "whiteQueen.svg",
                    ColorEnum.BLACK, "darkQueen.svg"
            ),
            Rook.class, Map.of(
                    ColorEnum.WHITE, "whiteRook.svg",
                    ColorEnum.BLACK, "darkRook.svg"
            ),
            Bishop.class, Map.of(
                    ColorEnum.WHITE, "whiteBishop.svg",
                    ColorEnum.BLACK, "darkBishop.svg"
            ),
            Knight.class, Map.of(
                    ColorEnum.WHITE, "whiteKnight.svg",
                    ColorEnum.BLACK, "darkKnight.svg"
            ),
            Pawn.class, Map.of(
                    ColorEnum.WHITE, "whitePawn.svg",
                    ColorEnum.BLACK, "darkPawn.svg"
            )
    );

    private final Map<String, SVGDocument> pieceImageCache = new HashMap<>();
    private GridPane boardGrid;

    public Region build(Consumer<Position> onSquareClicked, Board board) {
        GridPane grid = new GridPane();
        this.boardGrid = grid;

        renderBoard(board, onSquareClicked);
        return grid;
    }

    public void refresh(Board board, Consumer<Position> onSquareClicked) {
        if (boardGrid == null) {
            return;
        }

        boardGrid.getChildren().clear();
        renderBoard(board, onSquareClicked);
    }

    private void renderBoard(Board board, Consumer<Position> onSquareClicked) {
        Piece[][] pieces = board.getPieces();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                boolean isLightSquare = (row + col) % 2 == 0;
                Piece piece = pieces[row][col];
                Region square = createSquare(row, col, isLightSquare, piece, onSquareClicked);
                boardGrid.add(square, col, row);
            }
        }
    }

    private Region createSquare(int row, int col, boolean isLight, Piece piece,
                                Consumer<Position> onSquareClicked) {
        StackPane square = new StackPane();
        square.setPrefSize(60, 60);
        square.setStyle("-fx-background-color: " + (isLight ? "#eeeed2" : "#769656") + ";");

        if (piece != null) {
            square.getChildren().add(createPieceNode(piece));
        }

        square.setOnMouseClicked(event ->
                onSquareClicked.accept(new Position(row, col))
        );

        return square;
    }

    private Node createPieceNode(Piece piece) {
        String assetName = findPieceAssetName(piece);
        if (assetName == null) {
            return new Label(piece.toString());
        }

        String resourcePath = PIECE_ASSET_PATH + assetName;
        SVGDocument document = pieceImageCache.computeIfAbsent(resourcePath, this::loadSvgDocument);

        FXSVGCanvas canvas = new FXSVGCanvas();
        canvas.setRenderBackend(FXSVGCanvas.RenderBackend.JavaFX);
        canvas.setDocument(document);
        canvas.setPrefSize(PIECE_IMAGE_SIZE, PIECE_IMAGE_SIZE);
        canvas.setMinSize(PIECE_IMAGE_SIZE, PIECE_IMAGE_SIZE);
        canvas.setMaxSize(PIECE_IMAGE_SIZE, PIECE_IMAGE_SIZE);
        canvas.setAnimated(false);
        canvas.setMouseTransparent(true);
        return canvas;
    }

    private String findPieceAssetName(Piece piece) {
        Map<ColorEnum, String> assetsByColor = PIECE_ASSETS.get(piece.getClass());
        return assetsByColor == null ? null : assetsByColor.get(piece.getColor());
    }

    private SVGDocument loadSvgDocument(String resourcePath) {
        URL resource = BoardView.class.getResource(resourcePath);
        if (resource == null) {
            throw new IllegalStateException("Piece asset not found: " + resourcePath);
        }

        SVGDocument document = new SVGLoader().load(resource);
        if (document == null) {
            throw new IllegalStateException("Could not load piece asset: " + resourcePath);
        }

        return document;
    }

    public void highlightPossibleMoves(List<Position> moves) {
        clearPossibleMoveMarkers();

        for (Position move : moves) {
            StackPane square = findSquare(move.getX(), move.getY());
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
