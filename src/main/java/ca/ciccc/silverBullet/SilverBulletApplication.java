package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.gameplayEnums.PlayerAction;
import ca.ciccc.silverBullet.gridElements.GridBoard;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

/**
 * A very simple Java FX Application used to demonstrate a couple of aspects of the Java 11
 * ecosystem.
 */
public class SilverBulletApplication extends Application {

  private Pane root = new Pane();
  List<Node> children = Collections.synchronizedList(this.root.getChildren());
  Stage primaryStage;
  AnimationTimer timer;

  GridBoard gameBoard;
  Player testFirstPlayer;
  double t = 0;
  boolean isExecuting;
  int currentActionNumber = 0;

  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;
    Scene scene = new Scene(this.createContent());
    scene.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case Q:
          testFirstPlayer.addAction(PlayerAction.TURN_LEFT);
          break;
        case W:
          testFirstPlayer.addAction(PlayerAction.MOVE);
          break;
        case E:
          testFirstPlayer.addAction(PlayerAction.TURN_RIGHT);
          break;
        case R:
          testFirstPlayer.addAction(PlayerAction.SHOOT);
          break;
        case SPACE:
          currentActionNumber = 0;
          isExecuting = true;
          break;
      }
    });

    stage.setTitle("Silver Bullet");

    stage.setScene(scene);
    stage.show();
  }

  private Parent createContent() {
    this.root.setPrefSize(700, 700);

    gameBoard = new GridBoard(9, 9, 1);
    root.getChildren().add(gameBoard.gridBoard);
    testFirstPlayer = gameBoard.addPlayer(1, 1);
    root.getChildren().add(testFirstPlayer.playerNode);

    timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        update();
      }
    };

    timer.start();

    return this.root;
  }

  public void update() {
    if (isExecuting) {
      if (t <= 0) {
        t = .4;
        testFirstPlayer.takeAction(currentActionNumber);
        currentActionNumber++;
        if (currentActionNumber > 4) {
          isExecuting = false;
        }
      } else {
        t -= 0.016;
      }
    }
  }

  public static void main(String[] args) {
    launch();
  }

}



