package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.enums.gameplay.GridElement;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.logic.GameLogic;
import ca.ciccc.silverBullet.playerElements.Bullet;
import ca.ciccc.silverBullet.playerElements.CollisionBullet;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.ConstUtil.GridBoardSizeEnum;
import ca.ciccc.silverBullet.utils.LevelFileReadUtil;
import ca.ciccc.silverBullet.utils.MediaUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GridBoard {

  private GridNode[][] grid;
  public GridPane gridBoard;
  public List<Player> players;
  private GridNode[] playerStartLocation;
  private int gridSizeX;
  private int gridSizeY;
  private static final String PICKUP_IMAGE_PATH = "/images/Tiles/Pickup.png";

  GridBoard(int sizeX, int sizeY, int level) {
    this.generateBoard(sizeX, sizeY, level);

    this.players = new ArrayList<>();
    this.gridSizeX = sizeX - 1;
    this.gridSizeY = sizeY - 1;
  }

  public Move tryMovePlayer(Player playerToMove) {
    GridNode originGrid =
        grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()];

    if (!originGrid.hasPlayer()) {
      return null;
    }

    int[] destination = GameLogic.moveDestination(
        originGrid.getGridX(),
        originGrid.getGridY(),
        originGrid.getPlayerInSpace().getFacingDirection(),
        gridSizeX,
        gridSizeY,
        (x, y) -> grid[y][x].isCanMoveTo(),
        (x, y) -> grid[y][x].hasPlayer());

    return destination == null ? null : new Move(destination[0], destination[1]);
  }


  void movePlayer(Player playerToMove) {
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

    moveTransition.setFromX(startNode.getScreenX() + GridBoardSizeEnum.SPACE_TARGET_NODE_X.get());
    moveTransition.setFromY(startNode.getScreenY() + GridBoardSizeEnum.SPACE_TARGET_NODE_Y.get());

    moveTransition.setToX(targetNode.getScreenX() + GridBoardSizeEnum.SPACE_TARGET_NODE_X.get());
    moveTransition.setToY(targetNode.getScreenY() + GridBoardSizeEnum.SPACE_TARGET_NODE_Y.get());

    moveTransition.setDuration(Duration.seconds(.3));

    moveTransition.setInterpolator(Interpolator.EASE_OUT);

    moveTransition.setNode(playerToMove.getPlayerNode());

    moveTransition.play();

    if (targetNode.isHasPickup()){
      if(playerToMove.getNumberOfShots() < 3){

        playerToMove.addShot();
        pickupAquired(targetNode);
      }
    }

    playerToMove.setTargetMove(null);
  }

  private void generateBoard(int sizeX, int sizeY, int levelNumber) {
    this.grid = new GridNode[sizeY][sizeX];
    this.playerStartLocation = new GridNode[4];
    this.gridBoard = new GridPane();

    char[][] imageToPrint = LevelFileReadUtil.getLevelMapAry(levelNumber);

    for (int i = 0; i < sizeY; i++) {
      for (int j = 0; j < sizeX; j++) {
        GridNode nodeToAdd = GridElement.createGridNode(imageToPrint[i][j], j, i);
        this.gridBoard.add(nodeToAdd.getImage(), j, i);
        if(nodeToAdd.getPlayerStartPosition() > 0){
          this.playerStartLocation[nodeToAdd.getPlayerStartPosition()-1] = nodeToAdd;
        }
        this.grid[i][j] = nodeToAdd;
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

        if(grid[j][i].isHasPickup()){
          Image pickupImage = MediaUtil.createImage(PICKUP_IMAGE_PATH);
          Node pickupNode = new Circle(20, new ImagePattern(pickupImage));
          pickupNode.setTranslateX(grid[j][i].getScreenX() - GridBoardSizeEnum.BOARD_POSITION_X.get() + 10);
          pickupNode.setTranslateY(grid[j][i].getScreenY() - GridBoardSizeEnum.BOARD_POSITION_Y.get());
          grid[j][i].setPickupImage(pickupNode);
          gridBoard.getChildren().add(pickupNode);
        }
      }
    }
  }

  Player addPlayer(int gridX, int gridY, int playerNumber) {
    GridNode targetNode = grid[gridY][gridX];
    if (targetNode.hasPlayer()) {

      return null;
    }

    Player playerToAdd;
  if(gridY > gridSizeY / 2){
    playerToAdd =
        new Player(3, playerNumber, gridX, gridY, Directions.NORTH, this);
  } else {
    playerToAdd =
            new Player(3, playerNumber, gridX, gridY, Directions.SOUTH, this);
  }
    players.add(playerToAdd);
    targetNode.setPlayerInSpace(playerToAdd);

    playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + GridBoardSizeEnum.SPACE_TARGET_NODE_X.get());
    playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + GridBoardSizeEnum.SPACE_TARGET_NODE_Y.get());

    return playerToAdd;
  }

  // Package-private (not private) so the trajectory can be verified against a
  // real board in tests.
  Move tryShoot(Player playerShooting) {
    if (!playerShooting.isHasShot()) {
      return null;
    }

    int[] endpoint = GameLogic.shotEndpoint(
        playerShooting.getGridPositionX(),
        playerShooting.getGridPositionY(),
        playerShooting.getFacingDirection(),
        gridSizeX,
        gridSizeY,
        (x, y) -> grid[y][x].isCanMoveTo());

    return endpoint == null ? null : new Move(endpoint[0], endpoint[1]);
  }

  public void removePlayer(Player playerToRemove) {
    GameScene.instance.getChildren().remove(playerToRemove.getPlayerNode());
    playerToRemove.getPlayerActionCounter().blackout();
    GameScene.instance.timerDisplay.removePlayerImage(playerToRemove);

    detachPlayerFromBoard(playerToRemove)
        .ifPresent(GameScene.instance::showGameOver);
  }

  /**
   * Clears the tile the player occupies and drops it from the roster. Contains
   * no rendering side effects, so it can be unit-tested against a real board.
   *
   * @return the sole survivor's player number when only one player remains
   *     (the game-over condition), otherwise empty
   */
  OptionalInt detachPlayerFromBoard(Player playerToRemove) {
    getNodeFromGrid(playerToRemove.getGridPositionX(), playerToRemove.getGridPositionY())
        .setPlayerInSpace(null);
    players.remove(playerToRemove);
    return players.size() == 1
        ? OptionalInt.of(players.get(0).getPlayerNumber())
        : OptionalInt.empty();
  }

  private void pickupAquired(GridNode node){
    node.setHasPickup(false);
    gridBoard.getChildren().remove(node.getPickupImage());
  }

  public void shootBullet(Player player) {
    Move finalLocation = tryShoot(player);
    if (finalLocation == null) {
      return;
    }

    Bullet bulletToShoot = new Bullet(
            new Move(player.getGridPositionX(), player.getGridPositionY()),
            finalLocation,
            player,
            this
    );

    gridBoard.getChildren().add(bulletToShoot);
    gridBoard.getChildren().add(new CollisionBullet(new Move(player.getGridPositionX(), player.getGridPositionY()),
            finalLocation,
            player,
            bulletToShoot,
            this));
  }

  GridNode[] getPlayerStartLocation() {
    return playerStartLocation;
  }

  public GridNode getNodeFromGrid(int x, int y) {
    return grid[y][x];
  }

  boolean areAllFull() {
    return players.stream().allMatch(Player::isActionsFull);
  }
}
