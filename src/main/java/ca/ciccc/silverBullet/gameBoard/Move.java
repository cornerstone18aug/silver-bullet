package ca.ciccc.silverBullet.gameBoard;

public class Move{

    private int moveX;
    private int moveY;

    public Move(int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof  Move)) {
            return false;
        }
        Move otherMove = (Move)obj;
        return (moveX == otherMove.moveX && moveY == otherMove.moveY);
    }

    public int getMoveX() {
        return moveX;
    }
    public int getMoveY() {
        return moveY;
    }
}
