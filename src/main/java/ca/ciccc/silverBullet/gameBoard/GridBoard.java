package ca.ciccc.silverBullet.gameBoard;

import FileInput.FileInput;
import ca.ciccc.silverBullet.Player;
import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.gridNodes.Space;
import ca.ciccc.silverBullet.gridNodes.Wall;
import ca.ciccc.silverBullet.gridNodes.Water;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridBoard {
    GridNode[][] grid;
    public GridPane gridBoard;
    public List<Player> players;
    int gridSizeX;
    int gridSizeY;
    public static GridBoard instance;
    private FileInput fileInput = new FileInput();
    Random random = new Random();

    public GridBoard(int sizeX, int sizeY, int level) {
        generateBoard(sizeX, sizeY, level);
        players = new ArrayList<>();
        gridSizeX = sizeX -1;
        gridSizeY = sizeY-1;
        instance = this;

    }

    public Move tryMovePlayer(Player playerToMove){

        GridNode originGrid = grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()];
        int targetX = 0;
        int targetY = 0;
        GridNode targetNode;
        if (originGrid.hasPlayer()){
            switch (originGrid.getPlayerInSpace().getFacingDirection()){
                case NORTH:
                    targetX = originGrid.getGridX();
                    targetY = originGrid.getGridY() - 1;
                    break;
                case SOUTH:
                    targetX = originGrid.getGridX();
                    targetY = originGrid.getGridY() + 1;
                    break;
                case EAST:
                    targetX = originGrid.getGridX() + 1;
                    targetY = originGrid.getGridY();
                    break;
                case WEST:
                    targetX = originGrid.getGridX() - 1;
                    targetY = originGrid.getGridY();
                    break;
            }
            if((targetX <0 || targetY < 0) || (targetX > gridSizeX || targetY > gridSizeY)){
                return null;
            }
            targetNode = grid[targetY][targetX];

            if (targetX < 0 || targetY < 0) {
                return null;
            } else {
                if(targetNode.isCanMoveTo() && checkOtherPlayerMoves(playerToMove)){
                    return new Move(targetX, targetY);
                }
            }
        }

        return null;
    }

    public boolean checkOtherPlayerMoves(Player playerToMove){
        for(Player p : players){
            if(!p.equals(playerToMove)){
                if(p.getTargetMove() != null && ((p.getTargetMove().moveX == playerToMove.getTargetMove().moveX)&&(p.getTargetMove().moveY == playerToMove.getTargetMove().moveY))){
                    return false;
                }
            }
        }
        return true;
    }

    public void movePlayer(Player playerToMove){
        if(playerToMove.getTargetMove() != null){
            GridNode targetNode = grid[playerToMove.getTargetMove().moveY][playerToMove.getTargetMove().moveX];
            grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()].setPlayerInSpace(null);
            targetNode.setPlayerInSpace(playerToMove);

            playerToMove.setGridPositionX(targetNode.getGridX());
            playerToMove.setGridPositionY(targetNode.getGridY());

            playerToMove.getPlayerNode().setTranslateX(targetNode.getScreenX() + 30);
            playerToMove.getPlayerNode().setTranslateY(targetNode.getScreenY() + 30);

            playerToMove.setTargetMove(null);
        }

    }

    /**
     *
     * @param sizeX
     * @param sizeY
     * @param levelNumber
     */
    public void generateBoard(int sizeX, int sizeY, int levelNumber) {
        grid = new GridNode[sizeY][sizeX];
        gridBoard = new GridPane();
        /*
         * Instance to read the level and
         * depend of it print another maps
         * for the players.
         */
        ca.ciccc.silverBullet.FileReader.FileRead read = new ca.ciccc.silverBullet.FileReader.FileRead();
        char[][] imageToPrint = read.getLevel(levelNumber);

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (imageToPrint[i][j] == 'S') {
                    GridNode nodeToAdd = new Space(i, j);
                    gridBoard.add(nodeToAdd.getImage(), j, i);
                    grid[i][j] = nodeToAdd;
                    nodeToAdd.setGridX(j);
                    nodeToAdd.setGridY(i);
                } else if (imageToPrint[i][j] == 'W') {
                    GridNode nodeToAdd = new Wall(j, i);
                    gridBoard.add(nodeToAdd.getImage(), j, i);
                    grid[i][j] = nodeToAdd;
                    nodeToAdd.setGridX(j);
                    nodeToAdd.setGridY(i);
                } else {
                    GridNode nodeToAdd = new Water(j, i);
                    gridBoard.add(nodeToAdd.getImage(), j, i);
                    grid[i][j] = nodeToAdd;
                    nodeToAdd.setGridX(j);
                    nodeToAdd.setGridY(i);
                }
            }
        }

        gridBoard.setTranslateX(50);
        gridBoard.setTranslateY(50);
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                grid[j][i].setScreenX((i * 60) + 50);
                grid[j][i].setScreenY((j * 60) + 50);
            }
        }
    }

    public Player addPlayer(int gridX, int gridY, int playerNumber) {
        GridNode targetNode = grid[gridY][gridX];
        if(targetNode.hasPlayer() == false){
            Player playerToAdd = new Player(true, playerNumber, gridX, gridY, Directions.SOUTH);
            players.add(playerToAdd);
            targetNode.setPlayerInSpace(playerToAdd);
            playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + 30);
            playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + 30);
            return playerToAdd;
        }
        return null;
    }

    public boolean tryShoot (Player playerShooting){
        if(playerShooting.isHasShot()){
            GridNode playerStartingNode = grid[playerShooting.getGridPositionY()][playerShooting.getGridPositionX()];
            List<GridNode> nodesAffected = new ArrayList<>();
            GridNode currentTargetNode;
            int gridIterator = 1;
            switch (playerShooting.getFacingDirection()){
                case NORTH:
                    while (true){
                        if(playerShooting.getGridPositionY() - gridIterator >= 0){
                            currentTargetNode = grid[playerStartingNode.getGridX()][playerStartingNode.getGridY()-gridIterator];
                            if(currentTargetNode.isCanMoveTo()){
                                nodesAffected.add(currentTargetNode);
                            } else {
                                break;
                            }
                            gridIterator++;
                        } else {
                            break;
                        }

                    }
                    break;
                case SOUTH:
                    while (playerShooting.getGridPositionY() + gridIterator <= gridSizeY){

                            currentTargetNode = grid[playerStartingNode.getGridX()][playerStartingNode.getGridY()+gridIterator];
                            if(currentTargetNode.isCanMoveTo()){
                                nodesAffected.add(currentTargetNode);
                            } else {
                                break;
                            }
                            gridIterator++;


                    }
                    break;
                case EAST:
                    while (playerShooting.getGridPositionX() + gridIterator <= gridSizeX){

                            currentTargetNode = grid[playerStartingNode.getGridX()+gridIterator][playerStartingNode.getGridY()];
                            if(currentTargetNode.isCanMoveTo()){
                                nodesAffected.add(currentTargetNode);
                            } else {
                                break;
                            }
                            gridIterator++;


                    }
                    break;
                case WEST:
                    while (playerShooting.getGridPositionX() - gridIterator >= 0){

                            currentTargetNode = grid[playerStartingNode.getGridX()-gridIterator][playerStartingNode.getGridY()];
                            if(currentTargetNode.isCanMoveTo()){
                                nodesAffected.add(currentTargetNode);
                            } else {
                                break;
                            }
                            gridIterator++;
                        }

            }
            nodesAffected.forEach((o ->{
                ((Rectangle)o.squareNode).setFill(Color.ORANGE);
                if(o.hasPlayer()){
                    o.getPlayerInSpace().Die();
                }
            }));
            //playerShooting.hasShot = false;
            return true;
        }
        return false;
    }
}

