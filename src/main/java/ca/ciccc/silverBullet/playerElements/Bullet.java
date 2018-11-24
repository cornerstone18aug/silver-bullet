package ca.ciccc.silverBullet.playerElements;

import FileInput.FileInput;
import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
import ca.ciccc.silverBullet.gameBoard.Move;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bullet extends Rectangle {

  public Bullet(Move startPosition, Move endPosition, Player player) {
      super(25, 25, 50, 50);
      GridNode startNode = GridBoard.instance.getNodeFromGrid(startPosition.getMoveX(), startPosition.getMoveY());
      GridNode endNode = GridBoard.instance.getNodeFromGrid(endPosition.getMoveX(), endPosition.getMoveY());
      setTranslateX(startNode.getScreenX());
      setTranslateY(startNode.getScreenY() - 50);
      shootMovement(startNode, endNode, player);
  }

  public void shootMovement(GridNode startPos, GridNode endPos, Player player) {
    FileInput fInput = new FileInput();
    TranslateTransition transition = new TranslateTransition();

    switch (player.getPlayerNumber()) {
      case 1:
        this.setFill(new ImagePattern(
            fInput.image("file:src/main/resources/images/Character/Fire/FireAttack.png")));
        break;
      case 2:
        this.setFill(new ImagePattern(
            fInput.image("file:src/main/resources/images/Character/Rock/RockAttack.png")));
        break;
      default:
        this.setFill(new ImagePattern(
            fInput.image("file:src/main/resources/images/Character/Fire/FireAttack.png")));
        break;
    }

    if (player.getFacingDirection().equals(Directions.SOUTH) || player.getFacingDirection()
        .equals(Directions.NORTH)) {

      transition.setFromX(startPos.getScreenX() - 45);
      transition.setToX(endPos.getScreenX() - 45);

    } else {
      transition.setFromX(startPos.getScreenX());
      transition.setToX(endPos.getScreenX());
    }

    transition.setFromY(startPos.getScreenY() - 50);
    transition.setInterpolator(Interpolator.EASE_IN);
    transition.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        onBulletStop();
      }
    });

    transition.setDuration(Duration.seconds(.5));
    transition.setToY(endPos.getScreenY() - 50);
    transition.setNode(this);
    transition.play();
  }

  public void onBulletStop() {
    GridBoard.instance.gridBoard.getChildren().remove(this);
  }
}
