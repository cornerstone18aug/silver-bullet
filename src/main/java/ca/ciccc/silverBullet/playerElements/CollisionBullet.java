package ca.ciccc.silverBullet.playerElements;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
import ca.ciccc.silverBullet.gameBoard.Move;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CollisionBullet extends Rectangle {

  AnimationTimer timer;
  Player playerShooting;

  public CollisionBullet(Move startPosition, Move endPosition, Player player) {
      super(25, 25, 50, 50);
      playerShooting = player;

      timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
          checkOverlap();
        }
      };

      timer.start();

      GridNode startNode = GridBoard.instance.getNodeFromGrid(startPosition.getMoveX(), startPosition.getMoveY());
      GridNode endNode = GridBoard.instance.getNodeFromGrid(endPosition.getMoveX(), endPosition.getMoveY());
      setTranslateX(startNode.getScreenX());
      setTranslateY(startNode.getScreenY());
      shootMovement(startNode, endNode, player);
  }

  public void shootMovement(GridNode startPos, GridNode endPos, Player player) {
    TranslateTransition transition = new TranslateTransition();


    this.setFill(Color.TRANSPARENT);
    if (player.getFacingDirection().equals(Directions.SOUTH) || player.getFacingDirection()
        .equals(Directions.NORTH)) {

      transition.setFromX(startPos.getScreenX());
      transition.setToX(endPos.getScreenX());

    } else {
      transition.setFromX(startPos.getScreenX());
      transition.setToX(endPos.getScreenX());
    }

    transition.setFromY(startPos.getScreenY());
    transition.setInterpolator(Interpolator.EASE_IN);
    transition.setOnFinished(e -> onBulletStop());

    transition.setDuration(Duration.seconds(.5));
    transition.setToY(endPos.getScreenY());
    transition.setNode(this);
    transition.play();
  }

  public void checkOverlap(){
    for(Player p : GridBoard.instance.players)
      if(!p.equals(playerShooting) && getBoundsInParent().intersects(p.getPlayerNode().getBoundsInParent())){
        p.Die();
        timer.stop();
        onBulletStop();
      }
  }

  public void onBulletStop() {
    GridBoard.instance.gridBoard.getChildren().remove(this);
  }
}
