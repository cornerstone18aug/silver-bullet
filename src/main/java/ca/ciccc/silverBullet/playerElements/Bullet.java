package ca.ciccc.silverBullet.playerElements;

import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.MediaUtil;
import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
import ca.ciccc.silverBullet.gameBoard.Move;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.utils.ConstUtil.BulletCoordinatesEnum;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bullet extends Rectangle {


  Player playerShooting;

  public Bullet(Move startPosition, Move endPosition, Player player) {
      super(25, 25, 50, 50);
      playerShooting = player;

      GridNode startNode = GridBoard.instance.getNodeFromGrid(startPosition.getMoveX(), startPosition.getMoveY());
      GridNode endNode = GridBoard.instance.getNodeFromGrid(endPosition.getMoveX(), endPosition.getMoveY());
      setTranslateX(startNode.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 10);
      setTranslateY(startNode.getScreenY() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_Y.get());
      shootMovement(startNode, endNode, player);
  }

  public void shootMovement(GridNode startPos, GridNode endPos, Player player) {
    TranslateTransition transition = new TranslateTransition();


    switch (player.getPlayerNumber()) {
      case 1:
        this.setFill(new ImagePattern(
            MediaUtil.createImage("file:src/main/resources/images/Character/Fire/FireAttack.png")));
        break;
      case 2:
        this.setFill(new ImagePattern(
            MediaUtil.createImage("file:src/main/resources/images/Character/Rock/RockAttack.png")));
        break;
      default:
        this.setFill(new ImagePattern(
            MediaUtil.createImage("file:src/main/resources/images/Character/Fire/FireAttack.png")));
        break;
    }

    if (player.getFacingDirection().equals(Directions.SOUTH) || player.getFacingDirection()
        .equals(Directions.NORTH)) {

      transition.setFromX(startPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);
      transition.setToX(endPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);

    } else {
      transition.setFromX(startPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);
      transition.setToX(endPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);
    }

    transition.setFromY(startPos.getScreenY() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_Y.get());
    transition.setInterpolator(Interpolator.EASE_IN);
    transition.setOnFinished(e -> onBulletStop());

    transition.setDuration(Duration.seconds(.5));
    transition.setToY(endPos.getScreenY()- ConstUtil.GridBoardSizeEnum.BOARD_POSITION_Y.get());
    transition.setNode(this);
    transition.play();
  }


  public void onBulletStop() {
    GridBoard.instance.gridBoard.getChildren().remove(this);
  }
}
