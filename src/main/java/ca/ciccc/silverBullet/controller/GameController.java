package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import ca.ciccc.silverBullet.gameBoard.GameScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * GameController
 * @author Masa
 */
public class GameController extends AbstractController {
  private static GameController instance;
  private static GameScene game;
  public AnimationTimer timer;

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

  void show(int players, int boardSize, int level) {
    game = new GameScene.Builder()
        .player(players)
        .boardSize(boardSize)
        .level(level).build();
    game.setStyle("-fx-background-color: #000000;");
    game.setPrefSize(900, 700);
    Scene scene = new Scene(game);
    scene.setOnKeyPressed(e -> game.onKeyPressed(e.getCode()));
    scene.setFill(Color.TRANSPARENT);

    timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        game.boardUpdate();
      }
    };
    timer.start();

    SilverBulletApp.primaryStage.setScene(scene);
  }

}
