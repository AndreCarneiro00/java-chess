package com.portfolio.domain.board;

import com.portfolio.domain.enums.ColorEnum;
import com.portfolio.domain.piece.Bishop;
import com.portfolio.domain.piece.Rook;
import com.portfolio.domain.piece.King;
import com.portfolio.domain.piece.Knight;
import com.portfolio.domain.piece.Queen;
import com.portfolio.domain.piece.base.Piece;
import com.portfolio.domain.piece.base.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private int size;
    private Piece[][] pieces;
    private Map<ColorEnum, List<Piece>> catchedPiecesByColor;

    public Board() {
        this.size = 8;
        this.pieces = new Piece[size][size];
        this.catchedPiecesByColor = new HashMap<>();
        catchedPiecesByColor.put(ColorEnum.WHITE, new ArrayList<>());
        catchedPiecesByColor.put(ColorEnum.BLACK, new ArrayList<>());
        initiate();
    }

    private void initiate() {
        for (int x = 0; x < size; x++) {
            ColorEnum color = null;
            if (x == 0 || x == 1) {
                color = ColorEnum.BLACK;
            }

            if (x == 6 || x == 7) {
                color = ColorEnum.WHITE;
            }

            for (int y = 0; y < size; y++) {
                Position position = new Position(x, y);

                if (x == 1 || x == 6) {
//                    pieces[x][y] = new Pawn(color, position);
                } else if (x == 0 || x == 7) {
                    pieces[x][y] = instanciatePiece(color, position);
                } else {
                    pieces[x][y] = null;
                }
            }
        }
    }

    private Piece instanciatePiece(ColorEnum color, Position position) {
        return switch (position.getY()) {
            case 0, 7 -> new Rook(color, position);
            case 1, 6 -> new Knight(color, position);
            case 2, 5 -> new Bishop(color, position);
            case 3 -> new Queen(color, position);
            case 4 -> new King(color, position);
            default -> null;
        };
    }

    private String display() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (pieces[x][y] == null) {
                    sb.append(".");
                } else {
                    sb.append(pieces[x][y]);
                }
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int getSize() {
        return size;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Map<ColorEnum, List<Piece>> getCatchedPiecesByColor() {
        return catchedPiecesByColor;
    }

    public void movePiece(Position current, Position next) {
        Piece currentPiece = pieces[current.getX()][current.getY()];
        Piece nextPiece = pieces[next.getX()][next.getY()];
        List<Position> possibleMoves = currentPiece.listPossibleMoves(this);
        if (possibleMoves.contains(next)) {
            pieces[current.getX()][current.getY()] = null;
            currentPiece.getPosition().setX(next.getX());
            currentPiece.getPosition().setY(next.getY());

            if (nextPiece != null) {
                catchedPiecesByColor.get(nextPiece.getColor()).add(nextPiece);
            }
            pieces[next.getX()][next.getY()] = currentPiece;
        }
    }

    @Override
    public String toString() {
        return display();
    }
}
