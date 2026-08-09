package ca.ciccc.silverBullet.logic;

import ca.ciccc.silverBullet.enums.gameplay.Directions;

/**
 * Pure board rules extracted from {@code GridBoard}.
 *
 * <p>These methods contain the game's movement and shooting decisions with no
 * dependency on JavaFX or on the mutable board singleton. The board is
 * described abstractly: its inclusive bounds ({@code maxX}/{@code maxY}) plus
 * {@link TilePredicate}s that answer questions about a coordinate. This keeps
 * the core rules unit-testable while {@code GridBoard} stays responsible for
 * translating the results into scene-graph updates.
 */
public final class GameLogic {

    private GameLogic() {}

    /** Answers a yes/no question about the tile at {@code (x, y)}. */
    @FunctionalInterface
    public interface TilePredicate {
        boolean test(int x, int y);
    }

    /**
     * Where a player standing at {@code (startX, startY)} and facing
     * {@code facing} ends up after a single MOVE action.
     *
     * <p>The move is one tile ahead. It is refused (returns {@code null}) when the
     * destination is off the board, impassable, or already occupied by another
     * player — mirroring {@code GridBoard.tryMovePlayer}.
     *
     * @param maxX inclusive maximum X coordinate on the board
     * @param maxY inclusive maximum Y coordinate on the board
     * @param passable whether a tile may be moved onto (walls/water/holes are not)
     * @param occupied whether a tile already holds a player
     * @return the destination as {@code {x, y}}, or {@code null} if blocked
     */
    public static int[] moveDestination(
            int startX,
            int startY,
            Directions facing,
            int maxX,
            int maxY,
            TilePredicate passable,
            TilePredicate occupied) {
        int targetX = startX + facing.dx();
        int targetY = startY + facing.dy();

        if (targetX < 0 || targetY < 0 || targetX > maxX || targetY > maxY) {
            return null;
        }
        if (!occupied.test(targetX, targetY) && passable.test(targetX, targetY)) {
            return new int[] {targetX, targetY};
        }
        return null;
    }

    /**
     * The tile a bullet fired from {@code (startX, startY)} facing {@code facing}
     * comes to rest on.
     *
     * <p>The shot walks tile-by-tile away from the shooter, passing over passable
     * tiles until it meets an impassable tile or the board edge, and stops on the
     * last passable tile it reached — mirroring {@code GridBoard.tryShoot}. If the
     * very next tile is impassable, there is no travel and {@code null} is
     * returned.
     *
     * @param maxX inclusive maximum X coordinate on the board
     * @param maxY inclusive maximum Y coordinate on the board
     * @param passable whether the bullet may travel over a tile
     * @return the endpoint as {@code {x, y}}, or {@code null} if the shot travels nowhere
     */
    public static int[] shotEndpoint(
            int startX, int startY, Directions facing, int maxX, int maxY, TilePredicate passable) {
        int lastX = -1;
        int lastY = -1;
        boolean reachedAny = false;

        for (int step = 1; ; step++) {
            int x = startX + facing.dx() * step;
            int y = startY + facing.dy() * step;

            if (x < 0 || y < 0 || x > maxX || y > maxY) {
                break;
            }
            if (!passable.test(x, y)) {
                break;
            }
            lastX = x;
            lastY = y;
            reachedAny = true;
        }

        return reachedAny ? new int[] {lastX, lastY} : null;
    }

    /**
     * Resolve a set of simultaneous player moves. Each entry of {@code targets}
     * is a player's destination tile as {@code {x, y}}, or {@code null} if that
     * player is not moving this step. A player may complete its move only if no
     * other player targets the same destination; when two or more contend for a
     * tile they collide and none of them moves. The decision is taken from this
     * snapshot, so it does not depend on the order moves are applied.
     *
     * @param targets each player's destination this step (null = not moving)
     * @return a parallel array: {@code true} where that player should move
     */
    public static boolean[] resolveSimultaneousMoves(int[][] targets) {
        boolean[] canMove = new boolean[targets.length];
        for (int i = 0; i < targets.length; i++) {
            if (targets[i] == null) {
                continue;
            }
            boolean contended = false;
            for (int j = 0; j < targets.length; j++) {
                if (j != i && targets[j] != null && targets[j][0] == targets[i][0] && targets[j][1] == targets[i][1]) {
                    contended = true;
                    break;
                }
            }
            canMove[i] = !contended;
        }
        return canMove;
    }
}
