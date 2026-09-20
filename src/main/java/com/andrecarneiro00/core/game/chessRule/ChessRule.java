package com.andrecarneiro00.core.game.chessRule;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.board.Move;
import com.andrecarneiro00.core.game.board.Position;
import com.andrecarneiro00.core.piece.King;
import com.andrecarneiro00.core.piece.base.Piece;

import java.util.List;

public final class ChessRule {
    public static boolean isCheckmate(Board board, ColorEnum color) {
        return isInCheck(board, color) && !hasAnyLegalMove(board, color);
    }

    public static boolean isStalemate(Board board, ColorEnum color) {
        return !isInCheck(board, color) && !hasAnyLegalMove(board, color);
    }

    public static boolean isInCheck(Board board, ColorEnum color) {
        Position kingPosition = findKingPosition(board, color);
        return board.isPositionUnderAttack(kingPosition, color);
    }

    public static boolean hasAnyLegalMove(Board board, ColorEnum color) {
        int size = board.getSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position position = new Position(row, col);
                Piece piece = board.pieceAt(position);

                if (piece != null
                        && piece.getColor() == color
                        && !listLegalMoves(board, position).isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<Position> listLegalMoves(Board board, Position position) {
        if (!position.isValid(board.getSize())) {
            return List.of();
        }

        Piece piece = board.pieceAt(position);
        if (piece == null) {
            return List.of();
        }

        return piece.listPossibleMoves(board).stream()
                .filter(target -> {
                    Board projectedBoard = board.project(new Move(position, target));
                    return !isInCheck(projectedBoard, piece.getColor());
                })
                .toList();
    }

    private static Position findKingPosition(Board board, ColorEnum color) {
        King king = board.searchKingByColor(color);
        if (king == null) {
            throw new IllegalStateException("King not found for color: " + color);
        }

        Position position = board.piecePosition(king);
        if (position == null) {
            throw new IllegalStateException("King position not found for color: " + color);
        }

        return position;
    }
}
