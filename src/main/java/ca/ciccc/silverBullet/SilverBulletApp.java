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
public class SilverBulletApp extends Application {

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

    try (InputStream is = Files.newInputStream(Paths.get("res/conan.png"))) {
      ImageView img = new ImageView(new Image(is));
      img.setFitWidth(DisplaySizeEnum.EXTERNAL_FRAME_W.get());
      img.setFitHeight(DisplaySizeEnum.EXTERNAL_FRAME_H.get());
      root.getChildren().add(img);
    } catch (IOException e) {
      System.out.println("Couldn't load image");
    }

    root.getChildren().addAll(
        new Title(),
        new MenuBox(Menu.createMenuItems())
    );

    return root;

  }


}
