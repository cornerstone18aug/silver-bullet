package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.gameBoard.GameScene;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
  private GameScene game = new GameScene();
  List<Node> children = Collections.synchronizedList(this.root.getChildren());
  Stage primaryStage;
  AnimationTimer timer;



  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;

    Scene scene = new Scene(this.createContent());

    scene.setOnKeyPressed(e -> {
      game.onKeyPressed(e.getCode());

    });

    stage.setTitle("Silver Bullet");

    stage.setScene(scene);
    stage.show();
  }

  private Parent createContent() {
    this.game.setPrefSize(700, 700);
    this.root.setPrefSize(800, 800);

    root.getChildren().add(new Rectangle(100, 100, Color.BLACK));

    timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        update();
      }
    };

    timer.start();

    return this.game;
  }

  public void update(){
    game.boardUpdate();
  }


  public static void main(String[] args) {
    launch();
  }



}



