package ca.ciccc.silverBullet.logic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.logic.GameLogic.TilePredicate;
import org.junit.jupiter.api.Test;

/**
 * Tests for the pure board rules. Boards are described as rows of characters:
 * <pre>
 *   '.' passable, empty
 *   '#' impassable (wall / water / hole)
 *   'X' passable but occupied by another player
 * </pre>
 * The top-left tile is {@code (0, 0)} and Y grows downward.
 */
class GameLogicTest {

  private static TilePredicate passable(String... rows) {
    return (x, y) -> rows[y].charAt(x) != '#';
  }

  private static TilePredicate occupied(String... rows) {
    return (x, y) -> rows[y].charAt(x) == 'X';
  }

  private static int maxX(String... rows) {
    return rows[0].length() - 1;
  }

  private static int maxY(String... rows) {
    return rows.length - 1;
  }

  // ---- moveDestination -----------------------------------------------------

  @Test
  void movesOntoAdjacentEmptyTileInEachDirection() {
    String[] board = {
        "...",
        "...",
        "..."
    };
    // From the centre (1,1), one step each way.
    assertArrayEquals(new int[] {1, 0},
        GameLogic.moveDestination(1, 1, Directions.NORTH, maxX(board), maxY(board), passable(board), occupied(board)));
    assertArrayEquals(new int[] {1, 2},
        GameLogic.moveDestination(1, 1, Directions.SOUTH, maxX(board), maxY(board), passable(board), occupied(board)));
    assertArrayEquals(new int[] {2, 1},
        GameLogic.moveDestination(1, 1, Directions.EAST, maxX(board), maxY(board), passable(board), occupied(board)));
    assertArrayEquals(new int[] {0, 1},
        GameLogic.moveDestination(1, 1, Directions.WEST, maxX(board), maxY(board), passable(board), occupied(board)));
  }

  @Test
  void refusesToMoveOffTheTopEdge() {
    String[] board = {"...", "...", "..."};
    assertNull(GameLogic.moveDestination(1, 0, Directions.NORTH, maxX(board), maxY(board), passable(board), occupied(board)));
  }

  @Test
  void refusesToMoveOffTheBottomEdge() {
    String[] board = {"...", "...", "..."};
    assertNull(GameLogic.moveDestination(1, 2, Directions.SOUTH, maxX(board), maxY(board), passable(board), occupied(board)));
  }

  @Test
  void refusesToMoveOffTheLeftAndRightEdges() {
    String[] board = {"...", "...", "..."};
    assertNull(GameLogic.moveDestination(0, 1, Directions.WEST, maxX(board), maxY(board), passable(board), occupied(board)));
    assertNull(GameLogic.moveDestination(2, 1, Directions.EAST, maxX(board), maxY(board), passable(board), occupied(board)));
  }

  @Test
  void refusesToMoveIntoAnImpassableTile() {
    String[] board = {
        ".#.",
        "...",
        "..."
    };
    // Facing NORTH from (1,1) into the wall at (1,0).
    assertNull(GameLogic.moveDestination(1, 1, Directions.NORTH, maxX(board), maxY(board), passable(board), occupied(board)));
  }

  @Test
  void refusesToMoveIntoATileHeldByAnotherPlayer() {
    String[] board = {
        "...",
        ".X.",
        "..."
    };
    // Facing EAST from (0,1) into the occupied tile at (1,1).
    assertNull(GameLogic.moveDestination(0, 1, Directions.EAST, maxX(board), maxY(board), passable(board), occupied(board)));
  }

  // ---- shotEndpoint --------------------------------------------------------

  @Test
  void shotTravelsToTheFarEdgeAcrossOpenGround() {
    String[] board = {"....."};
    // Shooter at (0,0) facing EAST on an open 5-wide row stops on the last tile.
    assertArrayEquals(new int[] {4, 0},
        GameLogic.shotEndpoint(0, 0, Directions.EAST, maxX(board), maxY(board), passable(board)));
  }

  @Test
  void shotStopsOnTheLastPassableTileBeforeAWall() {
    String[] board = {".#..."};
    // Wall sits at (1,0); the bullet from (0,0) EAST cannot even reach it, so
    // the adjacent-wall case applies below. Shoot from beyond instead:
    String[] board2 = {"...#."};
    // Shooter at (0,0) EAST: passes (1,0),(2,0), wall at (3,0) -> stops at (2,0).
    assertArrayEquals(new int[] {2, 0},
        GameLogic.shotEndpoint(0, 0, Directions.EAST, maxX(board2), maxY(board2), passable(board2)));
    // Sanity: with the wall adjacent the shot goes nowhere.
    assertNull(GameLogic.shotEndpoint(0, 0, Directions.EAST, maxX(board), maxY(board), passable(board)));
  }

  @Test
  void shotReturnsNullWhenTheNextTileIsImpassable() {
    String[] board = {"##"};
    assertNull(GameLogic.shotEndpoint(0, 0, Directions.EAST, maxX(board), maxY(board), passable(board)));
  }

  @Test
  void shotReturnsNullWhenFiredStraightOffTheEdge() {
    String[] board = {"...."};
    // Shooter already on the right edge facing further EAST.
    assertNull(GameLogic.shotEndpoint(3, 0, Directions.EAST, maxX(board), maxY(board), passable(board)));
  }

  @Test
  void shotTravelsUpwardStoppingBelowAWall() {
    // Column of 5 rows; wall at (0,1). Shooter at (0,4) facing NORTH.
    String[] board = {
        ".",
        "#",
        ".",
        ".",
        "."
    };
    // Passes (0,3),(0,2), wall at (0,1) -> stops at (0,2).
    assertArrayEquals(new int[] {0, 2},
        GameLogic.shotEndpoint(0, 4, Directions.NORTH, maxX(board), maxY(board), passable(board)));
  }

  @Test
  void shotTravelsWestToTheLeftEdge() {
    String[] board = {"....."};
    // Shooter at (4,0) facing WEST across open ground stops at the left edge.
    assertArrayEquals(new int[] {0, 0},
        GameLogic.shotEndpoint(4, 0, Directions.WEST, maxX(board), maxY(board), passable(board)));
  }
}
