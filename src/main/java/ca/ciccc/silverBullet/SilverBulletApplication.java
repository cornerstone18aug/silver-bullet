package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
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
  Player testSecondPlayer;
  double t = 0;
  boolean isExecuting;
  int currentActionNumber = 0;
  int controllingPlayer = 0;

  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;
    Scene scene = new Scene(this.createContent());
    scene.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case Q:
          if (controllingPlayer == 0){
            testFirstPlayer.addAction(PlayerAction.TURN_LEFT);
          } else{
            testSecondPlayer.addAction(PlayerAction.TURN_LEFT);
          }
          break;
        case W:
          if (controllingPlayer == 0){
            testFirstPlayer.addAction(PlayerAction.MOVE);
          } else{
            testSecondPlayer.addAction(PlayerAction.MOVE);
          }
          break;
        case E:
          if (controllingPlayer == 0){
            testFirstPlayer.addAction(PlayerAction.TURN_RIGHT);
          } else{
            testSecondPlayer.addAction(PlayerAction.TURN_RIGHT);
          }
          break;
        case R:
          if (controllingPlayer == 0){
            testFirstPlayer.addAction(PlayerAction.SHOOT);
          } else{
            testSecondPlayer.addAction(PlayerAction.SHOOT);
          }
          break;
        case SPACE:
          currentActionNumber = 0;
          isExecuting = true;
          break;
      }
      if(!testFirstPlayer.getPlayerActions()[4].equals(PlayerAction.NONE)){
        controllingPlayer++;
      }
    });

    stage.setTitle("Silver Bullet");

    stage.setScene(scene);
    stage.show();
  }

  private Parent createContent() {
    this.root.setPrefSize(700, 700);

    gameBoard = new GridBoard(9, 9);
    root.getChildren().add(gameBoard.gridBoard);
    testFirstPlayer = gameBoard.addPlayer(1, 1, 1);
    testSecondPlayer = gameBoard.addPlayer(5, 5, 2);

    root.getChildren().add(testFirstPlayer.playerNode);
    root.getChildren().add(testSecondPlayer.playerNode);

    root.getChildren().add(testSecondPlayer.getPlayerActionCounter());
    root.getChildren().add(testFirstPlayer.getPlayerActionCounter());

    testFirstPlayer.getPlayerActionCounter().setTranslateX(175);
    testFirstPlayer.getPlayerActionCounter().setTranslateY(630);

    testSecondPlayer.getPlayerActionCounter().setTranslateX(400);
    testSecondPlayer.getPlayerActionCounter().setTranslateY(630);

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
        testSecondPlayer.takeAction(currentActionNumber);
        currentActionNumber++;
        if (currentActionNumber > 4) {
          isExecuting = false;
          testFirstPlayer.getPlayerActionCounter().clearActions();
          testSecondPlayer.getPlayerActionCounter().clearActions();
          controllingPlayer = 0;
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



