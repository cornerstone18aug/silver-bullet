package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A move that is blocked by a collision must not linger and resolve on a later,
 * non-move action step. {@code takeAction} only sets {@code targetMove} for a
 * MOVE, and {@code movePlayer} only clears it for players that actually move —
 * so a blocked player used to keep its target and could "teleport" onto the
 * contested tile a step later while merely waiting.
 */
class GameSceneStaleMoveTest {

  @BeforeAll
  static void startJavaFx() throws InterruptedException {
    JavaFxToolkit.init();
  }

  /** Relocate a player onto a specific tile, keeping the grid consistent. */
  private static void placeAt(GridBoard board, Player player, int x, int y, Directions facing) {
    board.getNodeFromGrid(player.getGridPositionX(), player.getGridPositionY())
        .setPlayerInSpace(null);
    board.getNodeFromGrid(x, y).setPlayerInSpace(player);
    player.setGridPositionX(x);
    player.setGridPositionY(y);
    player.setFacingDirection(facing);
  }

  private static PlayerAction[] moveThenWait() {
    return new PlayerAction[] {
        PlayerAction.MOVE, PlayerAction.WAIT, PlayerAction.WAIT, PlayerAction.WAIT, PlayerAction.WAIT
    };
  }

  @Test
  void aBlockedMoveDoesNotResolveOnALaterWaitStep() throws InterruptedException {
    AtomicReference<GameScene> ref = new AtomicReference<>();

    JavaFxToolkit.runOnFxThread(() -> {
      GameScene scene = new GameScene.Builder().player(2).level(1).build();
      ref.set(scene);
      GridBoard board = scene.getGameBoard();
      Player a = board.players.get(0);
      Player b = board.players.get(1);

      // Face each other across the open tile (4,2): A at (3,2) heading east,
      // B at (5,2) heading west. Both queue MOVE first, then wait.
      placeAt(board, a, 3, 2, Directions.EAST);
      placeAt(board, b, 5, 2, Directions.WEST);
      a.setPlayerActions(moveThenWait());
      b.setPlayerActions(moveThenWait());

      // Step 0 (MOVE): both target (4,2) and collide, so neither moves.
      a.takeAction(0);
      b.takeAction(0);
      scene.executeMove();

      // B leaves the contest (e.g. eliminated); A merely waits next step.
      board.players.remove(b);

      // Step 1 (WAIT): A does nothing this step.
      a.takeAction(1);
      scene.executeMove();
    });

    Player a = ref.get().getGameBoard().players.get(0);
    assertEquals(3, a.getGridPositionX(), "a waiting player must not move onto the old blocked tile");
    assertEquals(2, a.getGridPositionY());
  }
}
