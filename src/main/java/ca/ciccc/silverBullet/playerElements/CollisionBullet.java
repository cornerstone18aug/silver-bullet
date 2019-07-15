package ca.ciccc.silverBullet.playerElements;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
import ca.ciccc.silverBullet.gameBoard.Move;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CollisionBullet extends Rectangle {

  private AnimationTimer timer;
  private Player playerShooting;

  private Bullet visualBullet;

  public CollisionBullet(Move startPosition, Move endPosition, Player player, Bullet otherBulletRef) {
      super(5, 5);

      visualBullet = otherBulletRef;

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
      setTranslateX(startNode.getScreenX() + 5);
      setTranslateY(startNode.getScreenY());
      shootMovement(startNode, endNode, player);
  }

  private void shootMovement(GridNode startPos, GridNode endPos, Player player) {
    TranslateTransition transition = new TranslateTransition();


    this.setFill(Color.TRANSPARENT);
    if (player.getFacingDirection().equals(Directions.SOUTH) || player.getFacingDirection()
        .equals(Directions.NORTH)) {

      transition.setFromX(startPos.getScreenX() + 5);
      transition.setToX(endPos.getScreenX() + 5);

    } else {
      transition.setFromX(startPos.getScreenX() + 5);
      transition.setToX(endPos.getScreenX() + 5);
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
      Player playerToRemove = null;
    for(int i = 0; i < GridBoard.instance.players.size(); i++)
      if(!GridBoard.instance.players.get(i).equals(playerShooting) &&
              getBoundsInParent().intersects(GridBoard.instance.players.get(i).getPlayerNode().getBoundsInParent())){

          playerToRemove = GridBoard.instance.players.get(i);


      }
      if(playerToRemove != null){

          timer.stop();
          playerToRemove.Die();
          onBulletStop();
      }

  }

  private void onBulletStop() {
      timer.stop();
    GridBoard.instance.gridBoard.getChildren().remove(this);
    visualBullet.onBulletStop();
  }
}
