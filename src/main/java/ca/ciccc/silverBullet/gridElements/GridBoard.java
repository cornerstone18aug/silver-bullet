package ca.ciccc.silverBullet.gridElements;

import ca.ciccc.silverBullet.FileReader.FileRead;
import ca.ciccc.silverBullet.Player;
import ca.ciccc.silverBullet.gameplayEnums.Directions;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.gridNodes.Space;
import ca.ciccc.silverBullet.gridNodes.Wall;
import ca.ciccc.silverBullet.gridNodes.Water;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GridBoard {

  GridNode[][] grid;
  public GridPane gridBoard;
  static List<Player> players;
  int gridSizeX;
  int gridSizeY;
  public static GridBoard instance;

  public GridBoard(int sizeX, int sizeY, int levelSelected) {
    generateBoard(sizeX, sizeY, levelSelected);
    players = new ArrayList<>();
    gridSizeX = sizeX - 1;
    gridSizeY = sizeY - 1;
    instance = this;

  }

  public Move tryMovePlayer(Player playerToMove) {

    GridNode originGrid = grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()];
    int targetX = 0;
    int targetY = 0;
    GridNode targetNode;
    if (originGrid.hasPlayer()) {
      switch (originGrid.getPlayerInSpace().getFacingDirection()) {
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
      if ((targetX < 0 || targetY < 0) || (targetX > gridSizeX || targetY > gridSizeY)) {
        return null;
      }
      targetNode = grid[targetY][targetX];

      if (targetX < 0 || targetY < 0) {
        return null;
      } else {
        if (targetNode.isCanMoveTo()) {
          return new Move(targetX, targetY);
        }
      }
    }

    return null;
  }

  public void movePlayer(Player playerToMove) {
    if (playerToMove.getTargetMove() != null) {
      GridNode targetNode = grid[playerToMove.getTargetMove().moveY][playerToMove
          .getTargetMove().moveX];
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
    FileRead read = new FileRead();
    char[][] imageToPrint = read.getLevel(levelNumber);

    for (int i = 0; i < sizeY; i++) {
      for (int j = 0; j < sizeX; j++) {
        //for (int x = 0; x < imageToPrint.length; x++) {
          //for (int y = 0; y < imageToPrint.length; y++) {
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
          //}
        //}
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

  public Player addPlayer(int gridX, int gridY) {
    GridNode targetNode = grid[gridY][gridX];
    if (targetNode.hasPlayer() == false) {
      Player playerToAdd = new Player(true, 1, gridX, gridY, Directions.NORTH);
      players.add(playerToAdd);
      targetNode.setPlayerInSpace(playerToAdd);
      playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + 30);
      playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + 30);
      return playerToAdd;
    }
    return null;
  }

  public boolean tryShoot(Player playerShooting) {
    if (playerShooting.isHasShot()) {
      GridNode playerStartingNode = grid[playerShooting.getGridPositionY()][playerShooting
          .getGridPositionX()];
      List<GridNode> nodesAffected = new ArrayList<>();
      GridNode currentTargetNode;
      int gridIterator = 1;
      switch (playerShooting.getFacingDirection()) {
        case NORTH:
          while (true) {
            if (playerShooting.getGridPositionY() - gridIterator >= 0) {
              currentTargetNode = grid[playerStartingNode.getGridX()][playerStartingNode.getGridY()
                  - gridIterator];
              if (currentTargetNode.isCanMoveTo()) {
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
          while (playerShooting.getGridPositionY() + gridIterator <= gridSizeY) {

            currentTargetNode = grid[playerStartingNode.getGridX()][playerStartingNode.getGridY()
                + gridIterator];
            if (currentTargetNode.isCanMoveTo()) {
              nodesAffected.add(currentTargetNode);
            } else {
              break;
            }
            gridIterator++;


          }
          break;
        case EAST:
          while (playerShooting.getGridPositionX() + gridIterator <= gridSizeX) {

            currentTargetNode = grid[playerStartingNode.getGridX()
                + gridIterator][playerStartingNode.getGridY()];
            if (currentTargetNode.isCanMoveTo()) {
              nodesAffected.add(currentTargetNode);
            } else {
              break;
            }
            gridIterator++;


          }
          break;
        case WEST:
          while (playerShooting.getGridPositionX() - gridIterator >= 0) {

            currentTargetNode = grid[playerStartingNode.getGridX()
                - gridIterator][playerStartingNode.getGridY()];
            if (currentTargetNode.isCanMoveTo()) {
              nodesAffected.add(currentTargetNode);
            } else {
              break;
            }
            gridIterator++;
          }

      }
      nodesAffected.forEach((o -> {
        ((Rectangle) o.squareNode).setFill(Color.ORANGE);
        if (o.hasPlayer()) {
          o.getPlayerInSpace().Die();
        }
      }));
      //playerShooting.hasShot = false;
      return true;
    }
    return false;
  }
}
