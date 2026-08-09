package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import java.util.OptionalInt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Regression tests for {@link GridBoard#detachPlayerFromBoard(Player)}.
 *
 * <p>The board indexes tiles as {@code grid[y][x]} everywhere (see
 * {@code getNodeFromGrid}). Player removal previously used {@code grid[x][y]},
 * so it cleared the wrong tile whenever a player stood on an asymmetric
 * coordinate (x != y) — leaving the dead player's tile still marked occupied.
 * These tests build a real level-1 board and place a player at (2, 1) to pin
 * that behaviour down.
 */
class GridBoardDetachPlayerTest {

    private static final int BOARD = 9;

    @BeforeAll
    static void startJavaFx() throws InterruptedException {
        JavaFxToolkit.init();
    }

    @Test
    void clearsTheTileThePlayerActuallyStandsOn() {
        GridBoard board = new GridBoard(BOARD, BOARD, 1);
        Player standing = board.addPlayer(2, 1, 1); // asymmetric: x=2, y=1
        board.addPlayer(6, 7, 2); // a second player so one survives

        // Precondition: (2,1) is occupied and the transposed tile (1,2) is not.
        assertTrue(board.getNodeFromGrid(2, 1).hasPlayer(), "player's own tile should start occupied");
        assertFalse(board.getNodeFromGrid(1, 2).hasPlayer(), "transposed tile should be empty");

        board.detachPlayerFromBoard(standing);

        // The tile the player stood on must be cleared. Under the old grid[x][y]
        // bug this stayed occupied because grid[2][1] (i.e. tile (1,2)) was cleared.
        assertFalse(board.getNodeFromGrid(2, 1).hasPlayer(), "player's tile must be cleared on removal");
        assertFalse(board.players.contains(standing), "player must be dropped from the roster");
    }

    @Test
    void reportsTheSoleSurvivorAsTheWinner() {
        GridBoard board = new GridBoard(BOARD, BOARD, 1);
        Player one = board.addPlayer(2, 1, 1);
        board.addPlayer(6, 7, 2);

        OptionalInt winner = board.detachPlayerFromBoard(one);

        assertTrue(winner.isPresent(), "one player left means the game is over");
        assertEquals(2, winner.getAsInt(), "the remaining player wins");
    }

    @Test
    void reportsNoWinnerWhileMoreThanOnePlayerRemains() {
        GridBoard board = new GridBoard(BOARD, BOARD, 1);
        Player one = board.addPlayer(2, 1, 1);
        board.addPlayer(6, 7, 2);
        board.addPlayer(2, 7, 3);

        OptionalInt winner = board.detachPlayerFromBoard(one);

        assertFalse(winner.isPresent(), "two players remain, so the game continues");
        assertEquals(2, board.players.size());
    }
}
