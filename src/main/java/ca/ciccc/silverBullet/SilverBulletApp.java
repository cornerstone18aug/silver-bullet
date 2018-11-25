package ca.ciccc.silverBullet;

import FileInput.FileInput;
import ca.ciccc.silverBullet.controller.MenuController;
import ca.ciccc.silverBullet.utils.ConstUtil;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 * SilverBulletApp
 */
public class SilverBulletApp extends Application {

  public static Stage primaryStage;
  FileInput fileInput = new FileInput();
  public static void main(String[] args) {
    ConstUtil.setResourceBundle(ResourceBundle.getBundle("application"));
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    SilverBulletApp.primaryStage = primaryStage;
    primaryStage.setTitle(ConstUtil.APP_NAME);

    // Start Menu
    MenuController.getInstance().show();

    primaryStage.show();

  }

}
