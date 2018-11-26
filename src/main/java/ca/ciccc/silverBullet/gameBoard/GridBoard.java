package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.enums.gameplay.GridElement;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.playerElements.Bullet;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.utils.ConstUtil.GridBoardSizeEnum;
import ca.ciccc.silverBullet.utils.LevelFileReadUtil;
import ca.ciccc.silverBullet.utils.MediaUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class GridBoard {

  public GridNode[][] grid;
  public GridPane gridBoard;
  public List<Player> players;
  int gridSizeX;
  int gridSizeY;
  public static GridBoard instance;
  private MediaUtil mediaUtil = new MediaUtil();


  public GridBoard(int sizeX, int sizeY, int level) {
    generateBoard(sizeX, sizeY, level);
    players = new ArrayList<>();
    gridSizeX = sizeX - 1;
    gridSizeY = sizeY - 1;
    instance = this;
  }


  public Move tryMovePlayer(Player playerToMove) {

    GridNode originGrid =
        grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()];
    int targetX = 0;
    int targetY = 0;

    GridNode targetNode;
    if (originGrid.hasPlayer()) {
      switch (originGrid.getPlayerInSpace().getFacingDirection()) {
        case NORTH:
          targetX = originGrid.getGridX();
          targetY = originGrid.getGridY() - GridBoardSizeEnum.TILE_CORRECTION_COORDINATE.get();
          break;
        case SOUTH:
          targetX = originGrid.getGridX();
          targetY = originGrid.getGridY() + GridBoardSizeEnum.TILE_CORRECTION_COORDINATE.get();
          break;
        case EAST:
          targetX = originGrid.getGridX() + GridBoardSizeEnum.TILE_CORRECTION_COORDINATE.get();
          targetY = originGrid.getGridY();
          break;
        case WEST:
          targetX = originGrid.getGridX() - GridBoardSizeEnum.TILE_CORRECTION_COORDINATE.get();
          targetY = originGrid.getGridY();
          break;
      }
      if ((targetX < 0 || targetY < 0) || (targetX > gridSizeX || targetY > gridSizeY)) {
        return null;
      }
      targetNode = grid[targetY][targetX];

      if(!targetNode.hasPlayer() && targetNode.isCanMoveTo()) {
        return new Move(targetX, targetY);
      }
    }

    return null;
  }


  public void movePlayer(Player playerToMove) {
    if (playerToMove.getTargetMove() == null) {
      return;
    }
    TranslateTransition moveTransition = new TranslateTransition();

    GridNode startNode = grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()];
    GridNode targetNode = grid[playerToMove.getTargetMove().getMoveY()][playerToMove.getTargetMove().getMoveX()];


    grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()].setPlayerInSpace(null);
    targetNode.setPlayerInSpace(playerToMove);

    playerToMove.setGridPositionX(targetNode.getGridX());
    playerToMove.setGridPositionY(targetNode.getGridY());

    moveTransition.setFromX(startNode.getScreenX() + GridBoardSizeEnum.SPACE_TARGET_NODE.get());
    moveTransition.setFromY(startNode.getScreenY() + GridBoardSizeEnum.SPACE_TARGET_NODE.get());

    moveTransition.setToX(targetNode.getScreenX() + GridBoardSizeEnum.SPACE_TARGET_NODE.get());
    moveTransition.setToY(targetNode.getScreenY() + GridBoardSizeEnum.SPACE_TARGET_NODE.get());

    moveTransition.setDuration(Duration.seconds(.3));

    moveTransition.setInterpolator(Interpolator.EASE_OUT);

    moveTransition.setNode(playerToMove.getPlayerNode());

    moveTransition.play();

    System.out.println(targetNode.getGridX() + ", " + targetNode.getGridY());

    playerToMove.setTargetMove(null);

    playerToMove.setTargetMove(null);

  }

  public void generateBoard(int sizeX, int sizeY, int levelNumber) {
    grid = new GridNode[sizeY][sizeX];
    gridBoard = new GridPane();
    char[][] imageToPrint = LevelFileReadUtil.getLevelMapAry(levelNumber);

    for (int i = 0; i < sizeY; i++) {
      for (int j = 0; j < sizeX; j++) {
        GridNode nodeToAdd = GridElement.createGridNode(imageToPrint[i][j], j, i);
        gridBoard.add(nodeToAdd.getImage(), j, i);
        grid[i][j] = nodeToAdd;
        nodeToAdd.setGridX(j);
        nodeToAdd.setGridY(i);
      }
    }

    gridBoard.setTranslateX(GridBoardSizeEnum.BOARD_POSITION_X.get());
    gridBoard.setTranslateY(GridBoardSizeEnum.BOARD_POSITION_Y.get());
    for (int i = 0; i < sizeY; i++) {
      for (int j = 0; j < sizeX; j++) {
        grid[j][i].setScreenX((i * GridBoardSizeEnum.TILE_SIZE.get()) + GridBoardSizeEnum.BOARD_POSITION_X.get());
        grid[j][i].setScreenY((j * GridBoardSizeEnum.TILE_SIZE.get()) + GridBoardSizeEnum.BOARD_POSITION_Y.get());
      }
    }
  }

  public Player addPlayer(int gridX, int gridY, int playerNumber) {
    GridNode targetNode = grid[gridY][gridX];
    if (targetNode.hasPlayer()) {

      return null;
    }

    Player playerToAdd =
        new Player(true, playerNumber, gridX, gridY, Directions.SOUTH);
    players.add(playerToAdd);
    targetNode.setPlayerInSpace(playerToAdd);

    playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + GridBoardSizeEnum.SPACE_TARGET_NODE.get());
    playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + GridBoardSizeEnum.SPACE_TARGET_NODE.get());

    return playerToAdd;

  }

  public Move tryShoot(Player playerShooting) {
    if (!playerShooting.isHasShot()) {
      return null;
    }

    GridNode playerStartingNode = grid[playerShooting.getGridPositionY()][playerShooting
        .getGridPositionX()];
    List<GridNode> nodesAffected = new ArrayList<>();
    GridNode currentTargetNode;
    int gridIterator = 1;

    System.out.println(playerShooting.getFacingDirection().name());

    switch (playerShooting.getFacingDirection()) {
      case NORTH:
        while (true) {
          if (playerShooting.getGridPositionY() - gridIterator >= 0) {

            currentTargetNode = grid[playerStartingNode.getGridY()
                - gridIterator][playerStartingNode.getGridX()];
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

          currentTargetNode = grid[playerStartingNode.getGridY()
              + gridIterator][playerStartingNode.getGridX()];
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

          currentTargetNode = grid[playerStartingNode.getGridY()][playerStartingNode.getGridX()
              + gridIterator];
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

          currentTargetNode = grid[playerStartingNode.getGridY()][playerStartingNode.getGridX()
              - gridIterator];
          if (currentTargetNode.isCanMoveTo()) {
            nodesAffected.add(currentTargetNode);
          } else {
            break;
          }
          gridIterator++;
        }
    }

    Move resultMove = null;
    if (!nodesAffected.isEmpty()) {
      GridNode gn = nodesAffected.get(nodesAffected.size() - 1);
      resultMove = new Move(gn.getGridX(), gn.getGridY());
    }

    return resultMove;
  }

  public void removePlayer(Player playerToRemove) {
    GameScene.instance.getChildren().remove(playerToRemove.getPlayerNode());
  }

  public void shootBullet(Player player) {
    Move finalLocation = tryShoot(player);
    if (finalLocation == null) {
      return;
    }

    gridBoard.getChildren().add(new Bullet(
        new Move(player.getGridPositionX(), player.getGridPositionY()),
        finalLocation,
        player
    ));
  }

  public GridNode getNodeFromGrid(int x, int y) {
    return grid[y][x];
  }

  public boolean areAllFull() {
    return players.stream().allMatch(Player::isActionsFull);
  }
}
