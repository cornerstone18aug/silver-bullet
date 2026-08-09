package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Simultaneous-move collision resolution in {@link GameScene#executeMove()}.
 *
 * <p>When two players' moves target the same tile they should collide and
 * neither should move onto it. This is decided per action step across all
 * players at once.
 */
class GameSceneCollisionTest {

    @BeforeAll
    static void startJavaFx() throws InterruptedException {
        JavaFxToolkit.init();
    }

    @Test
    void twoPlayersTargetingTheSameTileWhileAThirdMovesElsewhere() throws InterruptedException {
        AtomicReference<GameScene> ref = new AtomicReference<>();

        JavaFxToolkit.runOnFxThread(() -> {
            GameScene scene = new GameScene.Builder().player(3).level(1).build();
            ref.set(scene);
            GridBoard board = scene.getGameBoard();

            // Starts: P0 (2,1), P1 (6,7), P2 (2,7).
            // P0 and P1 both aim for (4,4); P2 aims for the adjacent (3,7).
            board.players.get(0).setTargetMove(new Move(4, 4));
            board.players.get(1).setTargetMove(new Move(4, 4));
            board.players.get(2).setTargetMove(new Move(3, 7));

            scene.executeMove();
        });

        GridBoard board = ref.get().getGameBoard();
        Player p0 = board.players.get(0);
        Player p1 = board.players.get(1);
        Player p2 = board.players.get(2);

        // The colliding pair must stay put...
        assertEquals(2, p0.getGridPositionX(), "P0 should not move into the contested tile");
        assertEquals(1, p0.getGridPositionY());
        assertEquals(6, p1.getGridPositionX(), "P1 should not move into the contested tile");
        assertEquals(7, p1.getGridPositionY());
        assertFalse(board.getNodeFromGrid(4, 4).hasPlayer(), "the contested tile stays empty");

        // ...while the non-colliding player moves normally.
        assertEquals(3, p2.getGridPositionX());
        assertEquals(7, p2.getGridPositionY());
    }
}
