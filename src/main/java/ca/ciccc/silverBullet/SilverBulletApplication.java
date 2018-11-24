package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.gameBoard.GameScene;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A very simple Java FX Application used to demonstrate a couple of aspects of the Java 11
 * ecosystem.
 */
public class SilverBulletApplication extends Application {

  private GameScene game = new GameScene();
  Stage primaryStage;

  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;

    Scene scene = new Scene(this.createContent());

    scene.setOnKeyPressed(e -> game.onKeyPressed(e.getCode()));
    stage.setTitle("Silver Bullet");
    stage.setScene(scene);
    stage.show();
  }

  private Parent createContent() {
    this.game.setPrefSize(700, 700);
    game.setStyle("-fx-background-color: #000000");

    AnimationTimer timer = new AnimationTimer() {
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
