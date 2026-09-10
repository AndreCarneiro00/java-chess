package com.andrecarneiro00.game;

import com.andrecarneiro00.domain.board.Board;
import com.andrecarneiro00.domain.enums.ColorEnum;
import com.andrecarneiro00.domain.piece.base.Position;

public class App {
    static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);

        // Rook
//        System.out.println(board.getPieces()[0][0].listPossibleMoves(board).toString());
//        board.movePiece(new Position(0,0), new Position(7, 0));
//        System.out.println(board);
//        System.out.println(board.getPieces()[7][0].listPossibleMoves(board).toString());
//
//        board.movePiece(new Position(7,0), new Position(7, 1));
//        System.out.println(board);
//        System.out.println(board.getPieces()[7][1].listPossibleMoves(board).toString());

        //queen
//        System.out.println(board.getPieces()[0][3].listPossibleMoves(board).toString());
//        board.movePiece(new Position(0,3), new Position(5, 3));
//        System.out.println(board);
//        System.out.println(board.getPieces()[5][3].listPossibleMoves(board).toString());
//
//        System.out.println(board.getPieces()[5][3].listPossibleMoves(board).toString());
//        board.movePiece(new Position(5,3), new Position(6, 4));
//        System.out.println(board);
//        System.out.println(board.getPieces()[6][4].listPossibleMoves(board).toString());
//
//        System.out.println(board.getPieces()[6][4].listPossibleMoves(board).toString());
//        board.movePiece(new Position(6,4), new Position(7, 4));
//        System.out.println(board);
//        System.out.println(board.getPieces()[7][4].listPossibleMoves(board).toString());

        // king
//        System.out.println(board.getPieces()[0][4].listPossibleMoves(board).toString());
//        board.movePiece(new Position(0,4), new Position(1, 4));
//        System.out.println(board);
//        System.out.println(board.getPieces()[1][4].listPossibleMoves(board).toString());
//
//        board.movePiece(new Position(1,4), new Position(2, 4));
//        System.out.println(board);
//        System.out.println(board.getPieces()[2][4].listPossibleMoves(board).toString());

        // knight
//        System.out.println(board.getPieces()[0][1].listPossibleMoves(board).toString());

        // pawn
        System.out.println(board.getPieces()[1][1].listPossibleMoves(board).toString());
        board.movePiece(new Position(1,1), new Position(3, 1));
        System.out.println(board);
        System.out.println(board.getPieces()[3][1].listPossibleMoves(board).toString());

        board.movePiece(new Position(3,1), new Position(4, 2));
        System.out.println(board);
        System.out.println(board.getPieces()[4][2].listPossibleMoves(board).toString());

        System.out.println(board.getPieces()[1][2].listPossibleMoves(board).toString());
        board.movePiece(new Position(1,2), new Position(2, 2));
        System.out.println(board);
        System.out.println(board.getPieces()[2][2].listPossibleMoves(board).toString());


        System.out.println(board.getCatchedPiecesByColor().get(ColorEnum.BLACK).toString());
        System.out.println(board.getCatchedPiecesByColor().get(ColorEnum.WHITE).toString());
    }
}
