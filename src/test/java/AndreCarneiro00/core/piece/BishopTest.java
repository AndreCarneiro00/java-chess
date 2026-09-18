package andrecarneiro00.core.piece;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.Board;
import com.andrecarneiro00.core.game.boardInitializers.EmptyInitializer;
import com.andrecarneiro00.core.piece.Bishop;
import com.andrecarneiro00.core.piece.base.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BishopTest {
    Board board;
    @BeforeEach
    public void setUp() {
        board = new Board(8, new EmptyInitializer());
    }

    @Test
    public void givenBlockedBishop_whenPossibleMoves_thenReturnEmptyList() {
        Bishop bishop = new Bishop(ColorEnum.BLACK, new Position(3,3));
        Bishop block1 = new Bishop(ColorEnum.BLACK, new Position(4,4));
        Bishop block2 = new Bishop(ColorEnum.BLACK, new Position(2,2));
        Bishop block3 = new Bishop(ColorEnum.BLACK, new Position(4,2));
        Bishop block4 = new Bishop(ColorEnum.BLACK, new Position(2,4));
        board.getPieces()[3][3] = bishop;
        board.getPieces()[4][4] = block1;
        board.getPieces()[2][2] = block2;
        board.getPieces()[4][2] = block3;
        board.getPieces()[2][4] = block4;
        List<Position> possibleMoves = bishop.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenCornerBlockedBishop_whenPossibleMoves_thenReturnEmptyList() {
        Bishop bishop = new Bishop(ColorEnum.BLACK, new Position(0,0));
        Bishop block1 = new Bishop(ColorEnum.BLACK, new Position(1,1));
        board.getPieces()[0][0] = bishop;
        board.getPieces()[1][1] = block1;
        List<Position> possibleMoves = bishop.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());

        bishop = new Bishop(ColorEnum.BLACK, new Position(7,7));
        block1 = new Bishop(ColorEnum.BLACK, new Position(6,6));
        board.getPieces()[7][7] = bishop;
        board.getPieces()[6][6] = block1;
        possibleMoves = bishop.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenBishop_whenPossibleMoves_thenCanCaptureInFourDirections() {
        Bishop bishop = new Bishop(ColorEnum.BLACK, new Position(3,3));
        Bishop enemy1 = new Bishop(ColorEnum.WHITE, new Position(4,4));
        Bishop enemy2 = new Bishop(ColorEnum.WHITE, new Position(2,2));
        Bishop enemy3 = new Bishop(ColorEnum.WHITE, new Position(4,2));
        Bishop enemy4 = new Bishop(ColorEnum.WHITE, new Position(2,4));
        board.getPieces()[3][3] = bishop;
        board.getPieces()[4][4] = enemy1;
        board.getPieces()[2][2] = enemy2;
        board.getPieces()[4][2] = enemy3;
        board.getPieces()[2][4] = enemy4;
        List<Position> possibleMoves = bishop.listPossibleMoves(board);
        assertEquals(4, possibleMoves.size());
    }

    @Test
    public void givenOneDirectionFreeBishop_whenPossibleMoves_thenCanMoveInOneDirection() {
        Bishop bishop = new Bishop(ColorEnum.BLACK, new Position(3,3));
        Bishop block1 = new Bishop(ColorEnum.BLACK, new Position(2,2));
        Bishop block2 = new Bishop(ColorEnum.BLACK, new Position(4,2));
        Bishop block3 = new Bishop(ColorEnum.BLACK, new Position(2,4));
        board.getPieces()[3][3] = bishop;
        board.getPieces()[2][2] = block1;
        board.getPieces()[4][2] = block2;
        board.getPieces()[2][4] = block3;
        List<Position> possibleMoves = bishop.listPossibleMoves(board);
        assertEquals(4, possibleMoves.size());
    }
}
