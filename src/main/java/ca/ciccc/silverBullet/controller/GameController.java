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

    void show(int players, int level, int turnSeconds) {
        game = new GameScene.Builder()
                .player(players)
                .level(level)
                .turnSeconds(turnSeconds)
                .build();
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
        game.setOnStop(timer::stop);
        timer.start();

        SilverBulletApp.primaryStage.setScene(scene);
    }
}
