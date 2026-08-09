package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for ammo pickups on a real level-1 board. Moving onto a
 * pickup tile grants a shot (up to the cap of 3) and consumes the pickup.
 *
 * <p>Level 1 has pickup tiles ('P') at (2,4), (4,4), (6,4), (4,3) and (4,6).
 * These tests move a player from (3,4) east onto the pickup at (4,4).
 * {@code movePlayer} starts an animation, so it is run on the FX thread.
 */
class GridBoardPickupTest {

  private static final int BOARD = 9;
  private static final int PICKUP_X = 4;
  private static final int PICKUP_Y = 4;

  @BeforeAll
  static void startJavaFx() throws InterruptedException {
    JavaFxToolkit.init();
  }

  private static Player movingPlayer(int shots) {
    Player player = new Player(shots, 1, 3, PICKUP_Y, Directions.EAST); // start at (3,4)
    player.setTargetMove(new Move(PICKUP_X, PICKUP_Y));
    return player;
  }

  @Test
  void movingOntoAPickupGrantsAShotAndConsumesIt() throws InterruptedException {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player player = movingPlayer(1);

    assertTrue(board.getNodeFromGrid(PICKUP_X, PICKUP_Y).isHasPickup(), "pickup should start present");

    JavaFxToolkit.runOnFxThread(() -> board.movePlayer(player));

    assertEquals(2, player.getNumberOfShots(), "ammo should increase by one");
    assertFalse(board.getNodeFromGrid(PICKUP_X, PICKUP_Y).isHasPickup(), "pickup should be consumed");
    assertEquals(PICKUP_X, player.getGridPositionX());
    assertEquals(PICKUP_Y, player.getGridPositionY());
    assertSame(player, board.getNodeFromGrid(PICKUP_X, PICKUP_Y).getPlayerInSpace());
  }

  @Test
  void aFullPlayerWalksOverThePickupWithoutConsumingIt() throws InterruptedException {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player player = movingPlayer(3); // already at the cap

    JavaFxToolkit.runOnFxThread(() -> board.movePlayer(player));

    assertEquals(3, player.getNumberOfShots(), "ammo stays at the cap");
    assertTrue(board.getNodeFromGrid(PICKUP_X, PICKUP_Y).isHasPickup(), "pickup remains for later");
    assertEquals(PICKUP_X, player.getGridPositionX());
  }

  @Test
  void movingOntoAPlainTileLeavesAmmoUnchanged() throws InterruptedException {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player player = new Player(1, 1, 1, 2, Directions.EAST); // (1,2) -> (2,2), both plain spaces
    player.setTargetMove(new Move(2, 2));

    JavaFxToolkit.runOnFxThread(() -> board.movePlayer(player));

    assertEquals(1, player.getNumberOfShots());
    assertEquals(2, player.getGridPositionX());
    assertEquals(2, player.getGridPositionY());
  }
}
