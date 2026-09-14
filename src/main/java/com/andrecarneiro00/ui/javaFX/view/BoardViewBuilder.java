package com.andrecarneiro00.ui.javaFX.view;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.Board;
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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BoardViewBuilder {
    private static final String PIECE_ASSET_PATH = "/com/andrecarneiro00/ui/javaFX/assets/";
    private static final double PIECE_IMAGE_SIZE = 45;
    private final Map<String, SVGDocument> pieceImageCache = new HashMap<>();
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

    private final GridPane boardGrid;

    public BoardViewBuilder(GridPane boardGrid) {
        this.boardGrid = boardGrid;
    }

    public void renderBoard(Board board, Consumer<Position> onSquareClicked) {
        Piece[][] pieces = board.getPieces();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Piece piece = pieces[row][col];
                Region square = createSquare(row, col, isLightSquare(row, col), piece, onSquareClicked);
                boardGrid.add(square, col, row);
            }
        }
    }

    public void renderChange(Position current, Position target, Piece piece) {
        StackPane currentSquare = findSquare(current);
        StackPane targetSquare = findSquare(target);

        if (currentSquare == null || targetSquare == null) {
            throw new IllegalStateException("Could not find changed squares in the board grid");
        }

        currentSquare.getChildren().clear();
        targetSquare.getChildren().clear();
        targetSquare.getChildren().add(createPieceNode(piece));
    }

    private StackPane findSquare(Position position) {
        for (Node node : boardGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            int row = rowIndex == null ? 0 : rowIndex;
            int col = colIndex == null ? 0 : colIndex;

            if (row == position.getRow()
                    && col == position.getCol()
                    && node instanceof StackPane square) {
                return square;
            }
        }

        return null;
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

    private boolean isLightSquare(int row, int col) {
        return (row + col) % 2 == 0;
    }

}
