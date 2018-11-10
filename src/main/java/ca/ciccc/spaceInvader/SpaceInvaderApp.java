package ca.ciccc.spaceInvader;

import java.util.Collections;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A very simple Java FX Application used to demonstrate a couple of aspects of the Java 11
 * ecosystem.
 */
public class SpaceInvaderApp extends Application {

  private Pane root = new Pane();
  List<Node> children = Collections.synchronizedList(this.root.getChildren());
  Stage primaryStage;
  AnimationTimer timer;

  private Sprite player = new Sprite(300, 550, 40, 40, Type.PLAYER, Color.BLUE);

  private double t = 0;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) {
    this.primaryStage = stage;
    Scene scene = new Scene(this.createContent());
    scene.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case A:
          player.moveLeft();
          break;
        case D:
          player.moveRight();
          break;
        case SPACE:
          this.shoot(player);
          break;
      }
    });
    stage.setTitle("Space Invader App");

    stage.setScene(scene);
    stage.show();
  }

  private Parent createContent() {
    this.root.setPrefSize(600, 600);
    this.root.getChildren().add(player);

    timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        update();
      }
    };

    timer.start();

    this.nextLevel();

    return this.root;
  }

  private void nextLevel() {
    for (int i = 0; i < 5; ++i) {
      this.root.getChildren().add(
          new Sprite(90 + i * 100, 150, 30, 30, Type.ENEMY, Color.RED)
      );
    }
  }


  synchronized private void update() {
    t += 0.016;

    this.children.stream()
        .map(n -> (Sprite) n)
        .forEach(sprite1 -> {
          switch (sprite1.type) {
            case ENEMY_BULLET:
              sprite1.moveDown();
              if (sprite1.getBoundsInParent().intersects(player.getBoundsInParent())) {
                player.dead = true;
                sprite1.dead = true;
              }
              break;

            case PLAYER_BULLET:
              sprite1.moveUp();
              this.children.stream()
                  .map(n -> (Sprite) n)
                  .filter(sprite -> sprite.type.isEnemy())
                  .forEach(enemy -> {
                    if (sprite1.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                      enemy.dead = true;
                      sprite1.dead = true;
                    }
                  });
              break;

            case ENEMY:
              if (t > 2) {
                if (Math.random() < 0.3) {
                  shoot(sprite1);
                }
              }
          }
        });

    this.children.removeIf(child -> ((Sprite) child).dead);

    if (t > 2) {
      t = 0;
    }

    if (this.player.dead) {
      Label secondLabel = new Label("Game Over..");

      StackPane secondaryLayout = new StackPane();
      secondaryLayout.getChildren().add(secondLabel);

      Scene secondScene = new Scene(secondaryLayout, 230, 100);

      // New window (Stage)
      Stage newWindow = new Stage();
      newWindow.setTitle("Game Over..");
      newWindow.setScene(secondScene);

      // Specifies the modality for new window.
      newWindow.initModality(Modality.WINDOW_MODAL);

      // Specifies the owner Window (parent) for new window
      newWindow.initOwner(primaryStage);

      // Set position of second window, related to primary window.
      newWindow.setX(primaryStage.getX() + 200);
      newWindow.setY(primaryStage.getY() + 100);

      newWindow.show();
      timer.stop();
    }
  }

  private void shoot(Sprite who) {
    this.children.add(
        new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), 5, 20,
            Type.getBullet(who.type), Color.BLACK));
  }

  private static class Sprite extends Rectangle {

    boolean dead = false;
    final Type type;

    Sprite(int x, int y, int w, int h, Type type, Color color) {
      super(w, h, color);

      this.type = type;
      setTranslateX(x);
      setTranslateY(y);

    }

    void moveRight() {
      setTranslateX(getTranslateX() + 5);
    }

    void moveLeft() {
      setTranslateX(getTranslateX() - 5);
    }

    void moveUp() {
      setTranslateY(getTranslateY() - 5);
    }

    void moveDown() {
      setTranslateY(getTranslateY() + 5);
    }


  }
}
