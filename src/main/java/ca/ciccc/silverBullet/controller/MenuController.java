package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import ca.ciccc.silverBullet.utils.ModalUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * MenuController
 *
 * @author Masa
 */
Hao-Tse/dev
public class MenuController extends AbstractController {
  private static MenuController instance;
  private static Scene SCENE;

  static {
    FXMLLoader fxmlLoader = new FXMLLoader();
    try {
      fxmlLoader.load(
          MenuController.class.getModule().getResourceAsStream("fxml/menu.fxml")
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
  public static MenuController getInstance() {
    synchronized (MenuController.class) {
      if (instance == null) {
        instance = new MenuController();
      }
      return instance;
    }
  }

  public void show() {
    SilverBulletApp.primaryStage.setScene(SCENE);
    menuClip.play();
  }

  @FXML
  public void OnStartClicked() {
    SettingsController.getInstance().show();
  }

  @FXML
  public void OnHowToPlayClicked() {
    HowToPlayController.getInstance().show();
  }

  @FXML
  public void OnQuitClicked() {
    ModalUtil.confirm(
        "QUIT",
        "Did you really want to quit?",
        () -> System.exit(1)
    );
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
