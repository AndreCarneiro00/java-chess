package andrecarneiro00.core.game;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.enums.MoveResultEnum;
import com.andrecarneiro00.core.game.Game;
import com.andrecarneiro00.core.game.Player;
import com.andrecarneiro00.core.game.board.initializers.EmptyInitializer;
import com.andrecarneiro00.core.game.board.Position;
import com.andrecarneiro00.core.piece.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(8, new EmptyInitializer());
    }

    @Test
    public void whenMovePiece_thenTurnChanges() {
        Player currentPlayer = game.getTurn();
        game.getBoard().getPieces()[1][1] = new Pawn(ColorEnum.BLACK, new Position(1, 1));
        game.movePiece(new Position(1,1), new Position(2,1));
        assertEquals(currentPlayer.getColor(), game.getTurn().getColor());
    }

    @Test
    public void givenNegativePosition_whenValidatingPosition_thenReturnFalse() {
        Position negativePosition = new Position(-1, -1);
        boolean bothNegatives = game.validateSelectedPosition(negativePosition);

        negativePosition = new Position(-1, 0);
        boolean negativeX = game.validateSelectedPosition(negativePosition);

        negativePosition = new Position(0, -1);
        boolean negativeY = game.validateSelectedPosition(negativePosition);

        assertAll(
                () -> assertFalse(bothNegatives, "Negative X and Y should be invalid"),
                () -> assertFalse(negativeX, "Negative X should be invalid"),
                () -> assertFalse(negativeY, "Negative Y should be invalid")
        );
    }

    @Test
    public void givenNegativeCoordinates_whenListPossibleMoves_thenReturnEmpty() {
        Position negativePosition = new Position(-1, -1);
        List<Position> bothNegativesMoves = game.listPossibleMovesByPosition(negativePosition);

        negativePosition = new Position(-1, 0);
        List<Position> negativeXMoves = game.listPossibleMovesByPosition(negativePosition);

        negativePosition = new Position(0, -1);
        List<Position> negativeYMoves = game.listPossibleMovesByPosition(negativePosition);

        assertAll(
                () -> assertTrue(bothNegativesMoves.isEmpty(), "Negative X and Y should be invalid"),
                () -> assertTrue(negativeXMoves.isEmpty(), "Negative X should be invalid"),
                () -> assertTrue(negativeYMoves.isEmpty(), "Negative Y should be invalid")
        );
    }

    @Test
    public void givenPositionIsBiggerThanBoard_whenListPossibleMoves_thenReturnEmpty() {
        int boardSize = game.getBoard().getSize();
        Position outOfBounds = new Position(boardSize, 0);
        List<Position> moves = game.listPossibleMovesByPosition(outOfBounds);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void givenPositionWithNoPiece_whenListPossibleMoves_thenReturnEmpty() {
        game.getBoard().getPieces()[3][3] = null;
        Position emptyPosition = new Position(3, 3);
        List<Position> moves = game.listPossibleMovesByPosition(emptyPosition);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void givenPositionWithIncorrectColor_whenMovePiece_thenReturnFalse() {
        game.getBoard().getPieces()[1][1] = new Pawn(ColorEnum.BLACK, new Position(1, 1));
        Position currentPosition = new Position(1, 1);
        Position nextPosition = new Position(2, 1);
        MoveResultEnum result = game.movePiece(currentPosition, nextPosition);
        assertEquals(MoveResultEnum.INVALID_MOVE, result);
    }

    @Test
    public void givenPathIsBlockedByAnotherPiece_whenMovePiece_thenReturnFalse() {
        game.getBoard().getPieces()[0][0] = new Pawn(ColorEnum.BLACK, new Position(0, 0));
        game.getBoard().getPieces()[1][0] = new Pawn(ColorEnum.BLACK, new Position(1, 0));
        Position currentPosition = new Position(0, 0);
        Position nextPosition = new Position(1, 0);
        MoveResultEnum result = game.movePiece(currentPosition, nextPosition);
        assertEquals(MoveResultEnum.INVALID_MOVE, result);
    }

    @Test
    public void givenPositionWithEnemy_whenMovePiece_thenCapture() {
        game.getBoard().getPieces()[1][1] = new Pawn(ColorEnum.WHITE, new Position(1, 1));
        game.getBoard().getPieces()[0][0] = new Pawn(ColorEnum.BLACK, new Position(0, 0));
        Position currentPosition = new Position(1, 1);
        Position nextPosition = new Position(0, 0);
        Player player1 = game.getTurn();
        game.movePiece(currentPosition, nextPosition);
        assertEquals(1, player1.getCapturedPieces().size());
    }

    @Test
    public void givenValidPosition_whenMovePiece_thenReturnTrue() {
        game.getBoard().getPieces()[1][1] = new Pawn(ColorEnum.WHITE, new Position(1, 1));
        game.getBoard().getPieces()[0][0] = new Pawn(ColorEnum.BLACK, new Position(0, 0));
        Position currentPosition = new Position(1, 1);
        Position nextPosition = new Position(0, 0);
        MoveResultEnum result = game.movePiece(currentPosition, nextPosition);
        assertEquals(MoveResultEnum.MOVED, result);
    }

    @Test
    public void givenMovedPawn_whenMovePiece_thenCannotAdvanceTwoPositions() {
        game.getBoard().getPieces()[7][7] = new Pawn(ColorEnum.WHITE, new Position(7, 7));
        Position currentPosition = new Position(7, 7);
        Position nextPosition = new Position(5, 7);
        game.movePiece(currentPosition, nextPosition);
        MoveResultEnum result = game.movePiece(nextPosition, new Position(3, 7));
        assertEquals(MoveResultEnum.INVALID_MOVE, result);
    }
}
