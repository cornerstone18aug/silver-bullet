package ca.ciccc.silverBullet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;
    Scene scene = new Scene(this.createContent());
    scene.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case Q:
          testFirstPlayer.rotatePlayer(Orientation.LEFT);
          break;
        case W:
          testFirstPlayer.targetMove = gameBoard.tryMovePlayer(testFirstPlayer);

          gameBoard.movePlayer(testFirstPlayer);
          break;
        case E:
          testFirstPlayer.rotatePlayer(Orientation.RIGHT);
          break;
        case SPACE:
          gameBoard.tryShoot(testFirstPlayer);
          break;
      }
    });

    stage.setTitle("Silver Bullet");

    stage.setScene(scene);
    stage.show();
  }

  private Parent createContent() {
    this.root.setPrefSize(700, 700);

    gameBoard = new GridBoard(6, 6);
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

  public void update(){

  }


  public static void main(String[] args) {
    launch();
  }



}



