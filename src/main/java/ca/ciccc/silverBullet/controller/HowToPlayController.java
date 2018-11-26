package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import ca.ciccc.silverBullet.utils.ConstUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

/**
 * HowToPlayController
 *
 * @author Masa
 */
public class HowToPlayController {

  private static HowToPlayController instance;
  private static Scene SCENE;

  static {
    FXMLLoader fxmlLoader = new FXMLLoader();
    try {
      fxmlLoader.load(
          HowToPlayController.class.getModule().getResourceAsStream("fxml/howToPlay.fxml")
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
   *
   * @return instance
   */
  public static HowToPlayController getInstance() {
    synchronized (HowToPlayController.class) {
      if (instance == null) {
        instance = new HowToPlayController();
      }
      return instance;
    }
  }

  public void show() {
    SilverBulletApp.primaryStage.setScene(SCENE);
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
    MenuController.getInstance().show();
  }

}
