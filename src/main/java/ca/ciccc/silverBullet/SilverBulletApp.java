package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.controller.MenuController;
import ca.ciccc.silverBullet.utils.ConstUtil;
import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * SilverBulletApp
 */
public class SilverBulletApp extends Application {

  public static Stage primaryStage;

  public static void main(String[] args) {
    ConstUtil.setResourceBundle(ResourceBundle.getBundle("ca/ciccc/silverBullet/application"));

    Application.launch();
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
