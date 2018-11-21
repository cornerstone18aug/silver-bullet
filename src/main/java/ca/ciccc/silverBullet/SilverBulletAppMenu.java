package ca.ciccc.silverBullet;

import ca.ciccc.silverBullet.components.menus.MenuBox;
import ca.ciccc.silverBullet.components.menus.Title;
import ca.ciccc.silverBullet.enums.Menu;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.ConstUtil.DisplaySizeEnum;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    Scene scene = new Scene(createParentPane());
    primaryStage.setTitle(ConstUtil.APP_NAME);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Parent createParentPane() {
    Pane root = new Pane();

    root.setPrefSize(DisplaySizeEnum.EXTERNAL_FRAME_W.get(),
        DisplaySizeEnum.EXTERNAL_FRAME_H.get());

    ImageView img = new ImageView("res/conan.png");
    img.setFitWidth(DisplaySizeEnum.MENU_IMAGE_W.get());
    img.setFitHeight(DisplaySizeEnum.MENU_IMAGE_H.get());
    // Plus margin
    img.setX(DisplaySizeEnum.EXTERNAL_FRAME_W.get() - (img.getFitWidth() + 30));
    img.setY(DisplaySizeEnum.EXTERNAL_FRAME_H.get() - (img.getFitHeight() + 5));
    root.getChildren().add(img);

    root.getChildren().addAll(
        new Title(),
        new MenuBox(Menu.createMenuItems())
    );

    return root;

  }


}
