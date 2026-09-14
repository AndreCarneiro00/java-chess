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
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BoardViewBuilder {
    private static final DataFormat CHESS_POSITION = new DataFormat("application/x-java-chess-position");
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
    private final Consumer<Position> onSquareClicked;
    private final BiPredicate<Position, Position> onPieceDropped;

    public BoardViewBuilder(
            GridPane boardGrid,
            Consumer<Position> onSquareClicked,
            BiPredicate<Position, Position> onPieceDropped
    ) {
        this.boardGrid = boardGrid;
        this.onSquareClicked = onSquareClicked;
        this.onPieceDropped = onPieceDropped;
    }

    public void renderBoard(Board board) {
        Piece[][] pieces = board.getPieces();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Piece piece = pieces[row][col];
                Region square = createSquare(row, col, isLightSquare(row, col), piece);
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
        targetSquare.getChildren().add(createPieceNode(piece, target));
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

    private Node createPieceNode(Piece piece, Position position) {
        String assetName = findPieceAssetName(piece);
        Node pieceNode;
        if (assetName == null) {
            pieceNode = new Label(piece.toString());
        } else {
            String resourcePath = PIECE_ASSET_PATH + assetName;
            SVGDocument document = pieceImageCache.computeIfAbsent(resourcePath, this::loadSvgDocument);

            FXSVGCanvas canvas = new FXSVGCanvas();
            canvas.setRenderBackend(FXSVGCanvas.RenderBackend.JavaFX);
            canvas.setDocument(document);
            canvas.setPrefSize(PIECE_IMAGE_SIZE, PIECE_IMAGE_SIZE);
            canvas.setMinSize(PIECE_IMAGE_SIZE, PIECE_IMAGE_SIZE);
            canvas.setMaxSize(PIECE_IMAGE_SIZE, PIECE_IMAGE_SIZE);
            canvas.setAnimated(false);
            canvas.setStyle("-fx-background-color: transparent;");
            pieceNode = canvas;
        }

        configureDragSource(pieceNode, position);
        return pieceNode;
    }

    private void configureDragSource(Node pieceNode, Position position) {
        pieceNode.setOnDragDetected(event -> {
            onSquareClicked.accept(position);

            Dragboard dragboard = pieceNode.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(CHESS_POSITION, serializePosition(position));
            dragboard.setContent(content);
            WritableImage dragView = createTransparentSnapshot(pieceNode);
            dragboard.setDragView(
                    dragView,
                    dragView.getWidth() / 2,
                    dragView.getHeight() / 2
            );

            pieceNode.setOpacity(0.6);
            event.consume();
        });

        pieceNode.setOnDragDone(event -> {
            pieceNode.setOpacity(1.0);
            event.consume();
        });
    }

    private WritableImage createTransparentSnapshot(Node pieceNode) {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        return pieceNode.snapshot(parameters, null);
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

    private Region createSquare(int row, int col, boolean isLight, Piece piece) {
        StackPane square = new StackPane();
        square.setPrefSize(60, 60);
        square.setStyle("-fx-background-color: " + (isLight ? "#eeeed2" : "#769656") + ";");

        Position position = new Position(row, col);
        if (piece != null) {
            square.getChildren().add(createPieceNode(piece, position));
        }

        square.setOnMouseClicked(event ->
                onSquareClicked.accept(position)
        );
        configureDropTarget(square, position);

        return square;
    }

    private void configureDropTarget(StackPane square, Position target) {
        square.setOnDragOver(event -> {
            Position current = readPosition(event.getDragboard());
            if (current != null && !current.equals(target)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        square.setOnDragDropped(event -> {
            Position current = readPosition(event.getDragboard());
            boolean moved = current != null && onPieceDropped.test(current, target);
            event.setDropCompleted(moved);
            event.consume();
        });
    }

    private String serializePosition(Position position) {
        return position.getRow() + "," + position.getCol();
    }

    private Position readPosition(Dragboard dragboard) {
        Object content = dragboard.getContent(CHESS_POSITION);
        if (!(content instanceof String coordinates)) {
            return null;
        }

        String[] values = coordinates.split(",");
        if (values.length != 2) {
            return null;
        }

        try {
            return new Position(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private boolean isLightSquare(int row, int col) {
        return (row + col) % 2 == 0;
    }

}
