package andrecarneiro00.core.game;

import com.andrecarneiro00.core.enums.ColorEnum;
import com.andrecarneiro00.core.game.Player;
import com.andrecarneiro00.core.piece.Pawn;
import com.andrecarneiro00.core.piece.base.Piece;
import com.andrecarneiro00.core.game.board.Position;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void givenSameColorPiece_whenCapture_thenCapturedPiecesEmpty() {
        Player player = new Player(ColorEnum.WHITE);
        Piece piece = new Pawn(ColorEnum.WHITE, new Position(0,0));
        player.capture(piece);
        assertEquals(0, player.getCapturedPieces().size());
    }

    @Test
    public void givenDifferentColorPiece_whenCapture_thenCapturedPiecesHasSizeOne() {
        Player player = new Player(ColorEnum.WHITE);
        Piece piece = new Pawn(ColorEnum.BLACK, new Position(0,0));
        player.capture(piece);
        assertEquals(1, player.getCapturedPieces().size());
    }
}
