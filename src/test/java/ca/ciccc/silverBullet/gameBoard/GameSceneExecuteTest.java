package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Drives a full plan-then-execute turn through {@link GameScene}.
 *
 * <p>The turn flow is gated by confirmation dialogs; here they are replaced with
 * an auto-confirming {@link Prompter} so the phases advance without real
 * windows. Both players are planned via {@code onKeyPressed} + SPACE, then the
 * execution phase is stepped by repeatedly calling {@code boardUpdate} (standing
 * in for the {@code AnimationTimer}). Everything runs on the FX thread.
 */
class GameSceneExecuteTest {

  /** Enough boardUpdate ticks to resolve all five action steps (~26 ticks each). */
  private static final int EXECUTE_TICKS = 160;

  @BeforeAll
  static void startJavaFx() throws InterruptedException {
    JavaFxToolkit.init();
  }

  @Test
  void aQueuedTurnThenMoveResolvesDuringExecution() throws InterruptedException {
    AtomicReference<GameScene> ref = new AtomicReference<>();

    JavaFxToolkit.runOnFxThread(() -> {
      GameScene scene = new GameScene.Builder().player(2).level(1).build();
      scene.setPrompter((title, message, onConfirm) -> onConfirm.run()); // auto-confirm
      ref.set(scene);

      // Player 1 (index 0) starts at (2,1) facing SOUTH. Queue: turn right (-> WEST),
      // then move (-> (1,1)), then wait out the turn.
      scene.onKeyPressed(KeyCode.E); // TURN_RIGHT
      scene.onKeyPressed(KeyCode.W); // MOVE
      scene.onKeyPressed(KeyCode.T); // WAIT
      scene.onKeyPressed(KeyCode.T); // WAIT
      scene.onKeyPressed(KeyCode.T); // WAIT
      scene.onKeyPressed(KeyCode.SPACE); // end player 1's turn

      // Player 2 (index 1) just waits its whole turn.
      for (int i = 0; i < 5; i++) {
        scene.onKeyPressed(KeyCode.T);
      }
      scene.onKeyPressed(KeyCode.SPACE); // both full -> execution begins

      // Step the execution phase to completion.
      for (int tick = 0; tick < EXECUTE_TICKS; tick++) {
        scene.boardUpdate();
      }
    });

    GameScene scene = ref.get();
    Player planner = scene.getGameBoard().players.get(0);
    Player waiter = scene.getGameBoard().players.get(1);

    // The turn-then-move resolved: player 1 turned WEST and advanced one tile.
    assertEquals(Directions.WEST, planner.getFacingDirection(), "TURN_RIGHT should face WEST");
    assertEquals(1, planner.getGridPositionX(), "MOVE west should reach column 1");
    assertEquals(1, planner.getGridPositionY(), "row is unchanged by a westward move");

    // The waiting player did not move.
    assertEquals(6, waiter.getGridPositionX());
    assertEquals(7, waiter.getGridPositionY());
  }
}
