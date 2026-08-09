package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for movement and shooting resolved against a real level-1
 * board. These exercise {@code GridBoard.tryMovePlayer} / {@code tryShoot}
 * end-to-end — i.e. the board wiring its real tiles (walls, edges, occupancy)
 * into the pure {@code GameLogic} rules.
 *
 * <p>Level 1 layout (x = column, y = row); {@code W} = wall (impassable):
 * <pre>
 *   y0  E E E E E E E E E
 *   y1  E S 1 S W S 4 S E
 *   y2  E S S S S S S S E
 *   y3  E S W S P S W S E
 *   y4  E S P S P S P S E
 *   y5  E S W S S S W S E
 *   y6  E S S S P S S S E
 *   y7  E S 3 S W S 2 S E
 *   y8  E E E E E E E E E
 * </pre>
 */
class GridBoardMoveShootTest {

  private static final int BOARD = 9;

  @BeforeAll
  static void startJavaFx() throws InterruptedException {
    JavaFxToolkit.init();
  }

  private static Player place(GridBoard board, int x, int y, int number, Directions facing) {
    Player player = board.addPlayer(x, y, number);
    player.setFacingDirection(facing);
    return player;
  }

  private static void assertMove(int expectedX, int expectedY, Move move) {
    assertEquals(expectedX, move.getMoveX(), "moveX");
    assertEquals(expectedY, move.getMoveY(), "moveY");
  }

  // ---- tryMovePlayer -------------------------------------------------------

  @Test
  void movesOntoTheAdjacentOpenTile() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player player = place(board, 1, 2, 1, Directions.EAST);

    assertMove(2, 2, board.tryMovePlayer(player));
  }

  @Test
  void movementIsBlockedByAWall() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player player = place(board, 1, 3, 1, Directions.EAST); // wall sits at (2,3)

    assertNull(board.tryMovePlayer(player));
  }

  @Test
  void movementIsBlockedByTheBoardEdge() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player player = place(board, 0, 4, 1, Directions.WEST); // would step off the left edge

    assertNull(board.tryMovePlayer(player));
  }

  @Test
  void movementIsBlockedByAnotherPlayer() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player mover = place(board, 4, 2, 1, Directions.SOUTH);
    place(board, 4, 3, 2, Directions.NORTH); // occupies the target tile

    assertNull(board.tryMovePlayer(mover));
  }

  // ---- tryShoot ------------------------------------------------------------

  @Test
  void shotTravelsAcrossOpenGroundToTheFarEdge() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player shooter = place(board, 1, 2, 1, Directions.EAST); // row 2 is fully open

    assertMove(8, 2, board.tryShoot(shooter));
  }

  @Test
  void shotStopsOnTheLastTileBeforeAWall() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player shooter = place(board, 3, 3, 1, Directions.EAST); // wall at (6,3); (4,3),(5,3) open

    assertMove(5, 3, board.tryShoot(shooter));
  }

  @Test
  void shotAtAnAdjacentWallGoesNowhere() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player shooter = place(board, 1, 3, 1, Directions.EAST); // wall immediately at (2,3)

    assertNull(board.tryShoot(shooter));
  }

  @Test
  void aPlayerWithNoAmmoCannotShoot() {
    GridBoard board = new GridBoard(BOARD, BOARD, 1);
    Player unarmed = new Player(0, 1, 3, 3, Directions.EAST); // zero shots

    assertNull(board.tryShoot(unarmed));
  }
}
