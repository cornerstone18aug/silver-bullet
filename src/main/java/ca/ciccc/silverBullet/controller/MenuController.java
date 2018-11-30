package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import ca.ciccc.silverBullet.utils.ModalUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * MenuController
 *
 * @author Masa
 */
public class MenuController extends AbstractMenuController {
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
    if (!AbstractMenuController.MENU_CLIP.isPlaying()) {
      MENU_CLIP.setCycleCount(Integer.MAX_VALUE);
      AbstractMenuController.MENU_CLIP.play();
    }

    if (AbstractMenuController.BATTLE_CLIP.isPlaying()) {
      BATTLE_CLIP.setCycleCount(Integer.MAX_VALUE);
      AbstractMenuController.BATTLE_CLIP.stop();
    }
    SilverBulletApp.primaryStage.setScene(SCENE);
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
  public void OnEntered(MouseEvent event) {
    Text text = (Text) ((StackPane)event.getSource()).getChildren().get(0);
    text.setText("▷ " + text.getText());
  }

  @FXML
  public void OnExited(MouseEvent event) {
    Text text = (Text) ((StackPane)event.getSource()).getChildren().get(0);
    text.setText(text.getText().replace("▷ ", ""));
  }

}
