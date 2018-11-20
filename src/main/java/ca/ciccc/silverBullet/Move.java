package ca.ciccc.silverBullet;

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
}
