package ca.ciccc.silverBullet;

public class Player {
    boolean hasShot;
    int playerNumber;
    Directions facingDirection;
    int gridPositionX;
    int gridPositionY;

    public Player(boolean hasShot, int playerNumber, int gridX, int gridY, Directions facingDirection) {
        this.hasShot = hasShot;
        this.playerNumber = playerNumber;
        this.facingDirection = facingDirection;
        gridPositionX = gridX;
        gridPositionY = gridY;
    }

    public void rotatePlayer(Orientation move){
        switch (facingDirection){
            case NORTH:
                if(move == Orientation.LEFT){
                    facingDirection = Directions.WEST;
                } else {
                    facingDirection = Directions.EAST;
                }
                break;
            case SOUTH:
                if(move == Orientation.LEFT){
                    facingDirection = Directions.EAST;
                } else {
                    facingDirection = Directions.WEST;
                }
                break;
            case EAST:
                if(move == Orientation.LEFT){
                    facingDirection = Directions.NORTH;
                } else {
                    facingDirection = Directions.SOUTH;
                }
                break;
            case WEST:
                if(move == Orientation.LEFT){
                    facingDirection = Directions.SOUTH;
                } else {
                    facingDirection = Directions.NORTH;
                }
                break;
        }
    }


}
