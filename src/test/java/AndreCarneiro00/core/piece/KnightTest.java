package andrecarneiro00.core.piece;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.board.initializers.EmptyInitializer;
import com.andrecarneiro00.core.piece.Knight;
import com.andrecarneiro00.core.game.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KnightTest {
    Board board;
    @BeforeEach
    public void setUp() {
        board = new Board(8, new EmptyInitializer());
    }

    @Test
    public void givenFreeKnight_whenListPossibleMoves_thenReturnEightMoves() {
        Knight knight = new Knight(ColorEnum.BLACK);
        board.getPieces()[3][3] = knight;
        List<Position> possibleMoves = knight.listPossibleMoves(board);
        assertEquals(8, possibleMoves.size());
    }

    @Test
    public void givenBlockedBlackKnight_whenListPossibleMoves_thenReturnZeroMoves() {
        Knight knight = new Knight(ColorEnum.BLACK);
        Knight block1 = new Knight(ColorEnum.BLACK);
        Knight block2 = new Knight(ColorEnum.BLACK);
        Knight block3 = new Knight(ColorEnum.BLACK);
        Knight block4 = new Knight(ColorEnum.BLACK);
        Knight block5 = new Knight(ColorEnum.BLACK);
        Knight block6 = new Knight(ColorEnum.BLACK);
        Knight block7 = new Knight(ColorEnum.BLACK);
        Knight block8 = new Knight(ColorEnum.BLACK);
        board.getPieces()[3][3] = knight;
        board.getPieces()[1][2] = block1;
        board.getPieces()[1][4] = block2;
        board.getPieces()[5][4] = block3;
        board.getPieces()[5][2] = block4;
        board.getPieces()[2][5] = block5;
        board.getPieces()[4][5] = block6;
        board.getPieces()[2][1] = block7;
        board.getPieces()[4][1] = block8;
        List<Position> possibleMoves = knight.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenCornerBlockedBlackKnight_whenListPossibleMoves_thenReturnZeroMoves() {
        Knight knight = new Knight(ColorEnum.BLACK);
        Knight block1 = new Knight(ColorEnum.BLACK);
        Knight block2 = new Knight(ColorEnum.BLACK);
        board.getPieces()[0][0] = knight;
        board.getPieces()[1][2] = block1;
        board.getPieces()[2][1] = block2;
        List<Position> possibleMoves = knight.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());

        knight = new Knight(ColorEnum.BLACK);
        block1 = new Knight(ColorEnum.BLACK);
        block2 = new Knight(ColorEnum.BLACK);
        board.getPieces()[7][7] = knight;
        board.getPieces()[6][5] = block1;
        board.getPieces()[5][6] = block2;
        possibleMoves = knight.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }
}
