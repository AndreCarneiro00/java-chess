package andrecarneiro00.core.piece;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.game.board.EmptyInitializer;
import com.andrecarneiro00.core.piece.Queen;
import com.andrecarneiro00.core.piece.base.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueenTest {
    Board board;
    @BeforeEach
    public void setUp() {
        board = new Board(8, new EmptyInitializer());
    }

    @Test
    public void givenBlockedQueen_whenPossibleMoves_thenReturnEmptyList() {
        Queen queen = new Queen(ColorEnum.BLACK, new Position(3,3));
        Queen block1 = new Queen(ColorEnum.BLACK, new Position(4,3));
        Queen block2 = new Queen(ColorEnum.BLACK, new Position(2,3));
        Queen block3 = new Queen(ColorEnum.BLACK, new Position(3,4));
        Queen block4 = new Queen(ColorEnum.BLACK, new Position(3,2));
        Queen block5 = new Queen(ColorEnum.BLACK, new Position(4,4));
        Queen block6 = new Queen(ColorEnum.BLACK, new Position(2,2));
        Queen block7 = new Queen(ColorEnum.BLACK, new Position(4,2));
        Queen block8 = new Queen(ColorEnum.BLACK, new Position(2,4));
        board.getPieces()[3][3] = queen;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        board.getPieces()[4][4] = block5;
        board.getPieces()[2][2] = block6;
        board.getPieces()[4][2] = block7;
        board.getPieces()[2][4] = block8;
        List<Position> possibleMoves = queen.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenCornerBlockedQueen_whenPossibleMoves_thenReturnEmptyList() {
        Queen queen = new Queen(ColorEnum.BLACK, new Position(0,0));
        Queen block1 = new Queen(ColorEnum.BLACK, new Position(0,1));
        Queen block2 = new Queen(ColorEnum.BLACK, new Position(1,0));
        Queen block3 = new Queen(ColorEnum.BLACK, new Position(1,1));
        board.getPieces()[0][0] = queen;
        board.getPieces()[0][1] = block1;
        board.getPieces()[1][0] = block2;
        board.getPieces()[1][1] = block3;
        List<Position> possibleMoves = queen.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());

        queen = new Queen(ColorEnum.BLACK, new Position(7,7));
        block1 = new Queen(ColorEnum.BLACK, new Position(7,6));
        block2 = new Queen(ColorEnum.BLACK, new Position(6,7));
        block3 = new Queen(ColorEnum.BLACK, new Position(6,6));
        board.getPieces()[7][7] = queen;
        board.getPieces()[7][6] = block1;
        board.getPieces()[6][7] = block2;
        board.getPieces()[6][6] = block3;
        possibleMoves = queen.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenQueen_whenPossibleMoves_thenCanCaptureInEightDirections() {
        Queen queen = new Queen(ColorEnum.BLACK, new Position(3,3));
        Queen block1 = new Queen(ColorEnum.WHITE, new Position(4,3));
        Queen block2 = new Queen(ColorEnum.WHITE, new Position(2,3));
        Queen block3 = new Queen(ColorEnum.WHITE, new Position(3,4));
        Queen block4 = new Queen(ColorEnum.WHITE, new Position(3,2));
        Queen block5 = new Queen(ColorEnum.WHITE, new Position(4,4));
        Queen block6 = new Queen(ColorEnum.WHITE, new Position(2,2));
        Queen block7 = new Queen(ColorEnum.WHITE, new Position(4,2));
        Queen block8 = new Queen(ColorEnum.WHITE, new Position(2,4));
        board.getPieces()[3][3] = queen;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        board.getPieces()[4][4] = block5;
        board.getPieces()[2][2] = block6;
        board.getPieces()[4][2] = block7;
        board.getPieces()[2][4] = block8;
        List<Position> possibleMoves = queen.listPossibleMoves(board);
        assertEquals(8, possibleMoves.size());
    }

    @Test
    public void givenOneDirectionFreeQueen_whenPossibleMoves_thenCanMoveInOneDirection() {
        Queen queen = new Queen(ColorEnum.BLACK, new Position(3,3));
        Queen block1 = new Queen(ColorEnum.BLACK, new Position(4,3));
        Queen block2 = new Queen(ColorEnum.BLACK, new Position(2,3));
        Queen block3 = new Queen(ColorEnum.BLACK, new Position(3,4));
        Queen block4 = new Queen(ColorEnum.BLACK, new Position(3,2));
        Queen block5 = new Queen(ColorEnum.BLACK, new Position(4,4));
        Queen block6 = new Queen(ColorEnum.BLACK, new Position(2,2));
        Queen block7 = new Queen(ColorEnum.BLACK, new Position(4,2));
        board.getPieces()[3][3] = queen;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        board.getPieces()[4][4] = block5;
        board.getPieces()[2][2] = block6;
        board.getPieces()[4][2] = block7;
        List<Position> possibleMoves = queen.listPossibleMoves(board);
        assertEquals(3, possibleMoves.size());
    }
}
