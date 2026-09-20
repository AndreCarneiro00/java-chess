package andrecarneiro00.core.piece.base;

import com.andrecarneiro00.core.game.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PositionTest {
    @Test
    public void givenInvalidPosition_whenIsValid_returnFalse() {
        Position negativePosition = new Position(-1, -1);
        boolean bothNegativesMoves = negativePosition.isValid(8);

        negativePosition = new Position(-1, 0);
        boolean negativeXMoves = negativePosition.isValid(8);

        negativePosition = new Position(0, -1);
        boolean negativeYMoves = negativePosition.isValid(8);

        Position outOfBondsPosition = new Position(8, 8);
        boolean bothOutOfBondsMoves = outOfBondsPosition.isValid(8);

        outOfBondsPosition = new Position(8, 0);
        boolean outOfBondsXMoves = outOfBondsPosition.isValid(8);

        outOfBondsPosition = new Position(0, 8);
        boolean outOfBondsYMoves = outOfBondsPosition.isValid(8);

        assertAll(
                () -> assertFalse(bothNegativesMoves, "Negative X and Y should be invalid"),
                () -> assertFalse(negativeXMoves, "Negative X should be invalid"),
                () -> assertFalse(negativeYMoves, "Negative Y should be invalid"),

                () -> assertFalse(bothOutOfBondsMoves, "X and Y should be less than the limit 8"),
                () -> assertFalse(outOfBondsXMoves, "X should be less than the limit 8"),
                () -> assertFalse(outOfBondsYMoves, "Y should be less than the limit 8")
        );
    }
}
