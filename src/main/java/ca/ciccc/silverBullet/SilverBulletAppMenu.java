package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.components.menus.MenuScene;
import ca.ciccc.silverBullet.utils.ConstUtil;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A very simple Java FX Application used to demonstrate a couple of aspects of the Java 11
 * ecosystem.
 */
public class SilverBulletAppMenu extends Application {

  public static void main(String[] args) {
    ConstUtil.setResourceBundle(ResourceBundle.getBundle("application"));
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle(ConstUtil.APP_NAME);
    primaryStage.setScene(new MenuScene());
    primaryStage.show();
  }

}
