package ca.ciccc.silverBullet.gameBoard;

public class Move{

    int moveX;
    int moveY;

    public Move(int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    @Override
    public boolean equals(Object obj) {
        Move otherMove = (Move)obj;
        return (moveX == otherMove.moveX && moveY == otherMove.moveY);
    }

    public int getMoveX() {
        return moveX;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }
}
