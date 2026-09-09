package com.portfolio.game;

import com.portfolio.domain.board.Board;
import com.portfolio.domain.enums.ColorEnum;
import com.portfolio.domain.piece.base.Position;

public class App {
    static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);

//        System.out.println(board.getPieces()[0][0].listPossibleMoves(board).toString());
//        board.movePiece(new Position(0,0), new Position(7, 0));
//        System.out.println(board);
//        System.out.println(board.getPieces()[7][0].listPossibleMoves(board).toString());
//
//        board.movePiece(new Position(7,0), new Position(7, 1));
//        System.out.println(board);
//        System.out.println(board.getPieces()[7][1].listPossibleMoves(board).toString());


        System.out.println(board.getPieces()[0][3].listPossibleMoves(board).toString());
        board.movePiece(new Position(0,3), new Position(5, 3));
        System.out.println(board);
        System.out.println(board.getPieces()[5][3].listPossibleMoves(board).toString());

        System.out.println(board.getPieces()[5][3].listPossibleMoves(board).toString());
        board.movePiece(new Position(5,3), new Position(6, 4));
        System.out.println(board);
        System.out.println(board.getPieces()[6][4].listPossibleMoves(board).toString());

        System.out.println(board.getPieces()[6][4].listPossibleMoves(board).toString());
        board.movePiece(new Position(6,4), new Position(7, 4));
        System.out.println(board);
        System.out.println(board.getPieces()[7][4].listPossibleMoves(board).toString());

        System.out.println(board.getCatchedPiecesByColor().get(ColorEnum.BLACK).toString());
        System.out.println(board.getCatchedPiecesByColor().get(ColorEnum.WHITE).toString());
    }
}
