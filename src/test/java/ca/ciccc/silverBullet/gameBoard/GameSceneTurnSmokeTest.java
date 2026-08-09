package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Smoke test for the planning phase of a {@link GameScene}.
 *
 * <p>It builds a full 2-player scene (background, board, players, action
 * counters, timer display) and drives the planning phase through
 * {@code onKeyPressed}, asserting the controlled player's queue fills. The
 * execution phase is driven by an {@code AnimationTimer} and gated by modal
 * dialogs whose OK buttons cannot be clicked from a test, so it is out of scope
 * here. Everything runs on the FX thread.
 */
class GameSceneTurnSmokeTest {

  @BeforeAll
  static void startJavaFx() throws InterruptedException {
    JavaFxToolkit.init();
  }

  private static GameScene buildScene(int players, int level) throws InterruptedException {
    AtomicReference<GameScene> scene = new AtomicReference<>();
    JavaFxToolkit.runOnFxThread(
        () -> scene.set(new GameScene.Builder().player(players).level(level).build()));
    return scene.get();
  }

  @Test
  void buildsAFullyAssembledSceneForTwoPlayers() throws InterruptedException {
    GameScene scene = buildScene(2, 1);

    assertFalse(scene.getChildren().isEmpty(), "scene graph should be populated");
    assertEquals(2, scene.getGameBoard().players.size(), "both players should be on the board");
  }

  @Test
  void planningKeysFillTheControlledPlayersActionQueue() throws InterruptedException {
    GameScene scene = buildScene(2, 1);

    JavaFxToolkit.runOnFxThread(() -> {
      scene.onKeyPressed(KeyCode.Q); // TURN_LEFT
      scene.onKeyPressed(KeyCode.W); // MOVE
      scene.onKeyPressed(KeyCode.E); // TURN_RIGHT
      scene.onKeyPressed(KeyCode.R); // SHOOT
      scene.onKeyPressed(KeyCode.T); // WAIT
    });

    Player controlled = scene.getGameBoard().players.get(0);
    assertTrue(controlled.isActionsFull(), "five actions should fill the queue");
    assertArrayEquals(
        new PlayerAction[] {
            PlayerAction.TURN_LEFT, PlayerAction.MOVE, PlayerAction.TURN_RIGHT,
            PlayerAction.SHOOT, PlayerAction.WAIT
        },
        controlled.getPlayerActions());
  }

  @Test
  void planningOnlyAffectsTheCurrentPlayerNotTheNextOne() throws InterruptedException {
    GameScene scene = buildScene(2, 1);

    JavaFxToolkit.runOnFxThread(() -> scene.onKeyPressed(KeyCode.W)); // MOVE for player 0 only

    Player first = scene.getGameBoard().players.get(0);
    Player second = scene.getGameBoard().players.get(1);
    assertEquals(PlayerAction.MOVE, first.getPlayerActions()[0]);
    assertFalse(first.isActionsFull());
    assertEquals(PlayerAction.NONE, second.getPlayerActions()[0], "the other player is untouched");
  }
}
