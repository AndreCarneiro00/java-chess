package andrecarneiro00.core.piece;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.board.Board;
import com.andrecarneiro00.core.game.board.initializers.EmptyInitializer;
import com.andrecarneiro00.core.piece.Pawn;
import com.andrecarneiro00.core.game.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PawnTest {
    Board board;
    @BeforeEach
    public void setUp() {
        board = new Board(8, new EmptyInitializer());
    }

    @Test
    public void givenNewPawn_whenPossibleMoves_thenCanMoveTwoPositions() {
        Pawn pawn = new Pawn(ColorEnum.BLACK, new Position(1,1));
        board.getPieces()[1][1] = pawn;
        List<Position> possibleMoves = pawn.listPossibleMoves(board);
        assertTrue(possibleMoves.contains(new Position(3, 1)));
    }

    @Test
    public void givenBlackPawn_whenPossibleMoves_thenCanOnlyMovePlusX() {
        Pawn pawn = new Pawn(ColorEnum.BLACK, new Position(4,1));
        board.getPieces()[4][1] = pawn;
        List<Position> possibleMoves = pawn.listPossibleMoves(board);
        assertTrue(
                possibleMoves.stream().
                        filter(possibleMove -> possibleMove.getRow() <= 4)
                        .toList()
                        .isEmpty()
        );
    }

    @Test
    public void givenBlackPawnWithDiagonalEnemies_whenPossibleMoves_thenCanCaptureDiagonally() {
        Pawn pawn = new Pawn(ColorEnum.WHITE, new Position(4,1));
        board.getPieces()[4][1] = pawn;
        List<Position> possibleMoves = pawn.listPossibleMoves(board);
        assertTrue(
                possibleMoves.stream().
                        filter(possibleMove -> possibleMove.getRow() >= 4)
                        .toList()
                        .isEmpty()
        );
    }

    @Test
    public void givenBlackPawnWithDiagonalAllies_whenPossibleMoves_thenCannotCaptureDiagonally() {
        Pawn pawn = new Pawn(ColorEnum.BLACK, new Position(4,1));
        Pawn enemy1 = new Pawn(ColorEnum.BLACK, new Position(5,2));
        Pawn enemy2 = new Pawn(ColorEnum.BLACK, new Position(5,0));
        board.getPieces()[4][1] = pawn;
        board.getPieces()[5][2] = enemy1;
        board.getPieces()[5][0] = enemy2;
        List<Position> possibleMoves = pawn.listPossibleMoves(board);
        assertFalse(possibleMoves.contains(new Position(5,2)));
        assertFalse(possibleMoves.contains(new Position(5,0)));
    }

    @Test
    public void givenBlackBlockedPawn_whenPossibleMoves_thenReturnEmptyList() {
        Pawn pawn = new Pawn(ColorEnum.BLACK, new Position(4,1));
        Pawn enemy1 = new Pawn(ColorEnum.BLACK, new Position(5,1));
        board.getPieces()[4][1] = pawn;
        board.getPieces()[5][1] = enemy1;
        List<Position> possibleMoves = pawn.listPossibleMoves(board);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void givenBlackPawnWhithOnePossibleMovement_whenPossibleMoves_thenReturnSizeOneList() {
        Pawn pawn = new Pawn(ColorEnum.BLACK, new Position(1,1));
        Pawn enemy1 = new Pawn(ColorEnum.BLACK, new Position(3,1));
        board.getPieces()[1][1] = pawn;
        board.getPieces()[3][1] = enemy1;
        List<Position> possibleMoves = pawn.listPossibleMoves(board);
        assertEquals(1, possibleMoves.size());
    }

    @Test
    public void givenPawnOnLimits_whenPossibleMoves_thenReturnEmptyList() {
        Pawn blackPawnLastPosition = new Pawn(ColorEnum.BLACK, new Position(7,0));
        Pawn whitePawnLastPosition = new Pawn(ColorEnum.WHITE, new Position(0,0));
        board.getPieces()[7][0] = blackPawnLastPosition;
        board.getPieces()[0][0] = whitePawnLastPosition;
        List<Position> blackPossibleMoves = blackPawnLastPosition.listPossibleMoves(board);
        List<Position> whitePossibleMoves = whitePawnLastPosition.listPossibleMoves(board);
        assertTrue(blackPossibleMoves.isEmpty());
        assertTrue(whitePossibleMoves.isEmpty());
    }
}
