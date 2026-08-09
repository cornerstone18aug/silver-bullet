package ca.ciccc.silverBullet.logic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/** Tests for {@link GameLogic#resolveSimultaneousMoves(int[][])}. */
class SimultaneousMovesTest {

    @Test
    void distinctTargetsAllMove() {
        int[][] targets = {{1, 1}, {2, 2}, {3, 3}};
        assertArrayEquals(new boolean[] {true, true, true}, GameLogic.resolveSimultaneousMoves(targets));
    }

    @Test
    void twoPlayersContestingATileBothStayPut() {
        int[][] targets = {{4, 4}, {4, 4}};
        assertArrayEquals(new boolean[] {false, false}, GameLogic.resolveSimultaneousMoves(targets));
    }

    @Test
    void aThirdPlayerElsewhereDoesNotUnblockTheCollidingPair() {
        // The bug: a third mover used to let the contending pair both move.
        int[][] targets = {{4, 4}, {4, 4}, {3, 7}};
        assertArrayEquals(new boolean[] {false, false, true}, GameLogic.resolveSimultaneousMoves(targets));
    }

    @Test
    void nonMoversAreNeverMoved() {
        int[][] targets = {null, {2, 2}, null};
        assertArrayEquals(new boolean[] {false, true, false}, GameLogic.resolveSimultaneousMoves(targets));
    }

    @Test
    void aSharedTargetBlocksAllContendersEvenThree() {
        int[][] targets = {{5, 5}, {5, 5}, {5, 5}};
        assertArrayEquals(new boolean[] {false, false, false}, GameLogic.resolveSimultaneousMoves(targets));
    }
}
