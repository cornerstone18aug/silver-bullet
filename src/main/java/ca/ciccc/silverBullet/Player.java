package ca.ciccc.silverBullet;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player {
    boolean hasShot;
    int playerNumber;
    Directions facingDirection;
    int gridPositionX;
    int gridPositionY;
    Move targetMove;
    Node playerNode;


    public Player(boolean hasShot, int playerNumber, int gridX, int gridY, Directions facingDirection) {
        this.hasShot = hasShot;
        this.playerNumber = playerNumber;
        this.facingDirection = facingDirection;
        gridPositionX = gridX;
        gridPositionY = gridY;
        playerNode = new Circle(40, Color.GREEN);
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
