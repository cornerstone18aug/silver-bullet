package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import ca.ciccc.silverBullet.gameBoard.GameScene;
import ca.ciccc.silverBullet.gameBoard.GameScene.Builder;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * GameController
 * @author Masa
 */
public class GameController {
  private static GameController instance;
  private static Scene SCENE;
  private static GameScene game;

  static {
    instance = new GameController();
  }

  /**
   * Return singleton instance
   * @return instance
   */
  public static GameController getInstance() {
    synchronized (GameController.class) {
      if (instance == null) {
        instance = new GameController();
      }
      return instance;
    }
  }

  public void show(int players, int boardSize, int level) {
    game = new Builder()
        .player(players)
        .boardSize(boardSize)
        .level(level).build();
    game.setStyle("-fx-background-color: #000000");
    game.setPrefSize(900, 700);
    Scene scene = new Scene(game);
    scene.setOnKeyPressed(e -> game.onKeyPressed(e.getCode()));
    scene.setFill(Color.TRANSPARENT);
    SCENE = scene;

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        game.boardUpdate();
      }
    };
    timer.start();

    SilverBulletApp.primaryStage.setScene(SCENE);
  }

}
