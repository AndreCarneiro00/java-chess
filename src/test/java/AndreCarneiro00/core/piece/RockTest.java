package andrecarneiro00.core.piece;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.board.initializers.EmptyInitializer;
import com.andrecarneiro00.core.piece.Rook;
import com.andrecarneiro00.core.game.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RockTest {
    Board board;
    @BeforeEach
    public void setUp() {
        board = new Board(8, new EmptyInitializer());
    }

    @Test
    public void givenBlockedRook_whenPossibleMoves_thenReturnEmptyList() {
        Rook rook = new Rook(ColorEnum.BLACK);
        Rook block1 = new Rook(ColorEnum.BLACK);
        Rook block2 = new Rook(ColorEnum.BLACK);
        Rook block3 = new Rook(ColorEnum.BLACK);
        Rook block4 = new Rook(ColorEnum.BLACK);
        board.getPieces()[3][3] = rook;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        List<Position> possibleMoves = rook.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenCornerBlockedRook_whenPossibleMoves_thenReturnEmptyList() {
        Rook rook = new Rook(ColorEnum.BLACK);
        Rook block1 = new Rook(ColorEnum.BLACK);
        Rook block2 = new Rook(ColorEnum.BLACK);
        board.getPieces()[0][0] = rook;
        board.getPieces()[0][1] = block1;
        board.getPieces()[1][0] = block2;
        List<Position> possibleMoves = rook.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());

        rook = new Rook(ColorEnum.BLACK);
        block1 = new Rook(ColorEnum.BLACK);
        block2 = new Rook(ColorEnum.BLACK);
        board.getPieces()[7][7] = rook;
        board.getPieces()[7][6] = block1;
        board.getPieces()[6][7] = block2;
        possibleMoves = rook.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenRook_whenPossibleMoves_thenCanCaptureInFourDirections() {
        Rook rook = new Rook(ColorEnum.BLACK);
        Rook block1 = new Rook(ColorEnum.WHITE);
        Rook block2 = new Rook(ColorEnum.WHITE);
        Rook block3 = new Rook(ColorEnum.WHITE);
        Rook block4 = new Rook(ColorEnum.WHITE);
        board.getPieces()[3][3] = rook;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        board.getPieces()[3][2] = block4;
        List<Position> possibleMoves = rook.listPossibleMoves(board);
        assertEquals(4, possibleMoves.size());
    }

    @Test
    public void givenOneDirectionFreeRook_whenPossibleMoves_thenCanMoveInOneDirection() {
        Rook rook = new Rook(ColorEnum.BLACK);
        Rook block1 = new Rook(ColorEnum.BLACK);
        Rook block2 = new Rook(ColorEnum.BLACK);
        Rook block3 = new Rook(ColorEnum.BLACK);
        board.getPieces()[3][3] = rook;
        board.getPieces()[4][3] = block1;
        board.getPieces()[2][3] = block2;
        board.getPieces()[3][4] = block3;
        List<Position> possibleMoves = rook.listPossibleMoves(board);
        assertEquals(3, possibleMoves.size());
    }
}
