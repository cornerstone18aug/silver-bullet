package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ciccc.silverBullet.extraScreens.GameOverScreen;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Game-over handling when players are eliminated. A shot's hit detection is
 * animation-driven and not unit-testable, so these tests drive its
 * deterministic consequence: removing a player (as {@code Player.Die} does) and
 * the last-player-standing game-over that follows.
 */
class GameSceneGameOverTest {

    @BeforeAll
    static void startJavaFx() throws InterruptedException {
        JavaFxToolkit.init();
    }

    private static boolean showsGameOver(GameScene scene) {
        return scene.getChildren().stream().anyMatch(node -> node instanceof GameOverScreen);
    }

    @Test
    void eliminatingDownToOnePlayerEndsTheGame() throws InterruptedException {
        AtomicReference<GameScene> ref = new AtomicReference<>();

        JavaFxToolkit.runOnFxThread(() -> {
            GameScene scene = new GameScene.Builder().player(2).level(1).build();
            ref.set(scene);
            GridBoard board = scene.getGameBoard();
            board.removePlayer(board.players.get(1)); // player 2 is knocked out
        });

        GameScene scene = ref.get();
        assertEquals(1, scene.getGameBoard().players.size(), "one survivor remains");
        assertEquals(1, scene.getGameBoard().players.get(0).getPlayerNumber(), "player 1 survives");
        assertTrue(showsGameOver(scene), "a game-over screen should be shown");
    }

    @Test
    void gameOverStopsTheInjectedGameLoop() throws InterruptedException {
        AtomicBoolean stopped = new AtomicBoolean(false);

        JavaFxToolkit.runOnFxThread(() -> {
            GameScene scene = new GameScene.Builder().player(2).level(1).build();
            scene.setOnStop(() -> stopped.set(true));
            GridBoard board = scene.getGameBoard();
            board.removePlayer(board.players.get(1)); // triggers game over
        });

        assertTrue(stopped.get(), "the game loop should be stopped when the game ends");
    }

    @Test
    void eliminatingOneOfThreePlayersContinuesTheGame() throws InterruptedException {
        AtomicReference<GameScene> ref = new AtomicReference<>();

        JavaFxToolkit.runOnFxThread(() -> {
            GameScene scene = new GameScene.Builder().player(3).level(1).build();
            ref.set(scene);
            GridBoard board = scene.getGameBoard();
            board.removePlayer(board.players.get(0));
        });

        GameScene scene = ref.get();
        assertEquals(2, scene.getGameBoard().players.size(), "two players remain");
        assertFalse(showsGameOver(scene), "the game should not be over yet");
    }
}
