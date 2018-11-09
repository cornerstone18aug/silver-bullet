package ca.ciccc.spaceInvader;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * A very simple Java FX Application used to demonstrate a couple of aspects of the Java 11
 * ecosystem.
 */
public class SpaceInvaderApp extends Application {

  private Pane root = new Pane();

  private Sprite player = new Sprite(300, 550, 40, 40, Type.PLAYER, Color.BLUE);

  private double t = 0;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) {
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
    stage.setScene(scene);
    stage.show();
  }

  private Parent createContent() {
    this.root.setPrefSize(600, 600);
    this.root.getChildren().add(player);

    AnimationTimer timer = new AnimationTimer() {
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

  private void update() {
    t += 0.016;

    this.root.getChildren().stream()
        .map(n -> (Sprite) n)
        .forEach(s -> {
          switch (s.type) {
            case ENEMY_BULLET:
              s.moveDown();
              if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                player.dead = true;
                s.dead = true;
              }
              break;

            case PLAYER_BULLET:
              s.moveUp();
              this.root.getChildren().stream()
                  .map(n -> (Sprite) n)
                  .filter(sprite -> sprite.type == Type.ENEMY)
                  .forEach(enemy -> {
                    if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                      enemy.dead = true;
                      s.dead = true;
                    }
                  });
              break;

            case ENEMY:
              if (t > 2) {
                if (Math.random() < 0.3) {
                  shoot(s);
                }
              }
          }
        });

    this.root.getChildren()
        .removeIf(child -> {
          Sprite sprite = (Sprite) child;
          if (sprite.type == Type.PLAYER && sprite.dead) {
            System.exit(0);
          }
          return sprite.dead;
        });

    if (t > 2) {
      t = 0;
    }
  }

  private void shoot(Sprite who) {
    this.root.getChildren().add(
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
