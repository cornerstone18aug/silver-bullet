package ca.ciccc.silverBullet;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GridBoard {
    GridNode[][] grid;
    GridPane gridBoard;
    static List<Player> players;

    public GridBoard(int sizeX, int sizeY) {
        generateBoard(sizeX, sizeY);
        players = new ArrayList<>();

    }

    public Move tryMovePlayer(Player playerToMove){
        GridNode originGrid = grid[playerToMove.gridPositionY][playerToMove.gridPositionX];
        int targetX = 0;
        int targetY = 0;
        GridNode targetNode;
        if (originGrid.isHasPlayer()){
            switch (originGrid.playerInSpace.facingDirection){
                case NORTH:
                    targetX = originGrid.gridX;
                    targetY = originGrid.gridY - 1;
                    break;
                case SOUTH:
                    targetX = originGrid.gridX;
                    targetY = originGrid.gridY + 1;
                    break;
                case EAST:
                    targetX = originGrid.gridX + 1;
                    targetY = originGrid.gridY;
                    break;
                case WEST:
                    targetX = originGrid.gridX - 1;
                    targetY = originGrid.gridY;
                    break;
            }
            targetNode = grid[targetY][targetX];

            if (targetX < 0 || targetY < 0) {
                return null;
            } else {
                if(targetNode.isCanMoveTo()){
                    return new Move(targetX, targetY);
                }
            }
        }

        return null;
    }

    public void movePlayer(Player playerToMove){
        if(playerToMove.targetMove != null){
            GridNode targetNode = grid[playerToMove.gridPositionY][playerToMove.gridPositionX];
            targetNode.playerInSpace = null;
            targetNode.playerInSpace = playerToMove;

            playerToMove.playerNode.setTranslateX(targetNode.screenX);
            playerToMove.playerNode.setTranslateY(targetNode.screenY);

            playerToMove.targetMove = null;
        }

    }

    public void generateBoard(int sizeX, int sizeY){
        grid = new GridNode[sizeY][sizeX];
        gridBoard = new GridPane();
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j<sizeX; j++){
                GridNode nodeToAdd = new Space(i, j);
                nodeToAdd.squareNode = new Rectangle(100, 100, (i+j) % 2 == 0 ? Color.PINK:Color.TEAL);
                gridBoard.add(nodeToAdd.squareNode, i, j);
                grid[i][j] = nodeToAdd;
                nodeToAdd.gridX = j;
                nodeToAdd.gridY = i;
            }
        }
        gridBoard.setTranslateX(50);
        gridBoard.setTranslateY(50);
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j<sizeX; j++) {
                grid[j][i].screenX = (i*100) + 50;
                grid[j][i].screenY = (j*100) + 50;
            }
        }
    }

    public Player addPlayer(int gridX, int gridY) {
        GridNode targetNode = grid[gridY][gridX];
        if(targetNode.hasPlayer == false){
            Player playerToAdd = new Player(true, 1, gridX, gridY, Directions.NORTH);
            players.add(playerToAdd);
            targetNode.playerInSpace = playerToAdd;
            playerToAdd.playerNode.setTranslateX(targetNode.screenX + 50);
            playerToAdd.playerNode.setTranslateY(targetNode.screenY + 50);
            return playerToAdd;
        }
        return null;
    }
}

