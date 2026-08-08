package ca.ciccc.silverBullet.enums.gameplay;

/**
 * The four directions a player can face on the board.
 *
 * <p>The board's Y axis grows downward (row 0 is the top), so {@code NORTH}
 * decreases Y and {@code SOUTH} increases it. The delta and rotation helpers
 * here are pure functions with no rendering dependency, which keeps the core
 * movement/turning rules testable in isolation.
 */
public enum Directions {
    NORTH, SOUTH, EAST, WEST;

    /**
     * Horizontal step for one tile of movement in this direction.
     *
     * @return -1 (west), +1 (east) or 0 (north/south)
     */
    public int dx() {
        switch (this) {
            case EAST:
                return 1;
            case WEST:
                return -1;
            default:
                return 0;
        }
    }

    /**
     * Vertical step for one tile of movement in this direction.
     *
     * @return -1 (north), +1 (south) or 0 (east/west)
     */
    public int dy() {
        switch (this) {
            case NORTH:
                return -1;
            case SOUTH:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Rotate 90 degrees. {@link Orientation#LEFT} turns counter-clockwise,
     * {@link Orientation#RIGHT} turns clockwise.
     *
     * @param orientation which way to turn
     * @return the direction faced after turning
     */
    public Directions rotate(Orientation orientation) {
        switch (this) {
            case NORTH:
                return orientation == Orientation.LEFT ? WEST : EAST;
            case SOUTH:
                return orientation == Orientation.LEFT ? EAST : WEST;
            case EAST:
                return orientation == Orientation.LEFT ? NORTH : SOUTH;
            case WEST:
                return orientation == Orientation.LEFT ? SOUTH : NORTH;
            default:
                throw new IllegalStateException("Unknown direction: " + this);
        }
    }
}
