package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.enums.Type;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
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
public class SilverBulletApp extends Application {

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    Scene scene = new Scene(createContent());
    primaryStage.setTitle("VIDEO GAME");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Parent createContent() {
    Pane root = new Pane();



    return root;
  }


}
