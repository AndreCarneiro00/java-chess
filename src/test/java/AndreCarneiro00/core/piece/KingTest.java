package andrecarneiro00.core.piece;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.game.boardInitializers.EmptyInitializer;
import com.andrecarneiro00.core.piece.King;
import com.andrecarneiro00.core.piece.base.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KingTest {
    Board board;
    @BeforeEach
    public void setUp() {
        board = new Board(8, new EmptyInitializer());
    }

    @Test
    public void givenBlockedKing_whenPossibleMoves_thenReturnEmptyList() {
        King king = new King(ColorEnum.BLACK, new Position(3,3));
        King block1 = new King(ColorEnum.BLACK, new Position(4,3));
        King block2 = new King(ColorEnum.BLACK, new Position(2,3));
        King block3 = new King(ColorEnum.BLACK, new Position(3,4));
        King block4 = new King(ColorEnum.BLACK, new Position(3,2));
        King block5 = new King(ColorEnum.BLACK, new Position(4,4));
        King block6 = new King(ColorEnum.BLACK, new Position(2,2));
        King block7 = new King(ColorEnum.BLACK, new Position(4,2));
        King block8 = new King(ColorEnum.BLACK, new Position(2,4));
        board.getPieces()[3][3] = king;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        board.getPieces()[4][4] = block5;
        board.getPieces()[2][2] = block6;
        board.getPieces()[4][2] = block7;
        board.getPieces()[2][4] = block8;
        List<Position> possibleMoves = king.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenCornerBlockedKing_whenPossibleMoves_thenReturnEmptyList() {
        King king = new King(ColorEnum.BLACK, new Position(0,0));
        King block1 = new King(ColorEnum.BLACK, new Position(0,1));
        King block2 = new King(ColorEnum.BLACK, new Position(1,0));
        King block3 = new King(ColorEnum.BLACK, new Position(1,1));
        board.getPieces()[0][0] = king;
        board.getPieces()[0][1] = block1;
        board.getPieces()[1][0] = block2;
        board.getPieces()[1][1] = block3;
        List<Position> possibleMoves = king.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());

        king = new King(ColorEnum.BLACK, new Position(7,7));
        block1 = new King(ColorEnum.BLACK, new Position(7,6));
        block2 = new King(ColorEnum.BLACK, new Position(6,7));
        block3 = new King(ColorEnum.BLACK, new Position(6,6));
        board.getPieces()[7][7] = king;
        board.getPieces()[7][6] = block1;
        board.getPieces()[6][7] = block2;
        board.getPieces()[6][6] = block3;
        possibleMoves = king.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenKing_whenPossibleMoves_thenCanCaptureInEightDirections() {
        King king = new King(ColorEnum.BLACK, new Position(3,3));
        King block1 = new King(ColorEnum.WHITE, new Position(4,3));
        King block2 = new King(ColorEnum.WHITE, new Position(2,3));
        King block3 = new King(ColorEnum.WHITE, new Position(3,4));
        King block4 = new King(ColorEnum.WHITE, new Position(3,2));
        King block5 = new King(ColorEnum.WHITE, new Position(4,4));
        King block6 = new King(ColorEnum.WHITE, new Position(2,2));
        King block7 = new King(ColorEnum.WHITE, new Position(4,2));
        King block8 = new King(ColorEnum.WHITE, new Position(2,4));
        board.getPieces()[3][3] = king;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        board.getPieces()[4][4] = block5;
        board.getPieces()[2][2] = block6;
        board.getPieces()[4][2] = block7;
        board.getPieces()[2][4] = block8;
        List<Position> possibleMoves = king.listPossibleMoves(board);
        assertEquals(8, possibleMoves.size());
    }

    @Test
    public void givenOneDirectionFreeKing_whenPossibleMoves_thenCanMoveInOneDirection() {
        King king = new King(ColorEnum.BLACK, new Position(3,3));
        King block1 = new King(ColorEnum.BLACK, new Position(4,3));
        King block2 = new King(ColorEnum.BLACK, new Position(2,3));
        King block3 = new King(ColorEnum.BLACK, new Position(3,4));
        King block4 = new King(ColorEnum.BLACK, new Position(3,2));
        King block5 = new King(ColorEnum.BLACK, new Position(4,4));
        King block6 = new King(ColorEnum.BLACK, new Position(2,2));
        King block7 = new King(ColorEnum.BLACK, new Position(4,2));
        board.getPieces()[3][3] = king;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        board.getPieces()[4][4] = block5;
        board.getPieces()[2][2] = block6;
        board.getPieces()[4][2] = block7;
        List<Position> possibleMoves = king.listPossibleMoves(board);
        assertEquals(1, possibleMoves.size());
    }
}
