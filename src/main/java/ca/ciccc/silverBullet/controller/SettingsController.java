package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * HowToPlayController
 * @author Masa
 */
public class SettingsController extends AbstractController {
  private static SettingsController instance;
  private static Scene SCENE;

  static {
    FXMLLoader fxmlLoader = new FXMLLoader();
    try {
      fxmlLoader.load(
          SettingsController.class.getModule().getResourceAsStream("fxml/settings.fxml")
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
    Parent parent = fxmlLoader.getRoot();
    Scene scene = new Scene(parent);
    scene.setFill(Color.TRANSPARENT);
    SCENE = scene;
    instance = fxmlLoader.getController();
  }

  /**
   * Return singleton instance
   * @return instance
   */
  public static SettingsController getInstance() {
    synchronized (SettingsController.class) {
      if (instance == null) {
        instance = new SettingsController();
      }
      return instance;
    }
  }

  public void show() {
    SilverBulletApp.primaryStage.setScene(SCENE);
  }

  @FXML
  public void onStartClicked() {
    GameController.getInstance().show();
  }

  @FXML
  public void onBackToMenuClicked() {
    MenuController.getInstance().show();
  }

  @FXML
  public void OnEntered() {
    System.out.println("Entered");
  }

  @FXML
  public void OnPressed() {
    System.out.println("Pressed");
  }

  @FXML
  public void OnExited() {
    System.out.println("Exited");
  }

}
