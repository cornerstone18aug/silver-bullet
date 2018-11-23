package ca.ciccc.silverBullet.menus;

import ca.ciccc.silverBullet.enums.MenuItemEnum;
import ca.ciccc.silverBullet.utils.ConstUtil.DisplaySizeEnum;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * MenuScene
 *
 * @author Masa
 */
public class MenuScene extends Scene {

  public MenuScene() {
    super(new Pane());
    Pane root = (Pane) this.getRoot();

    root.setPrefSize(DisplaySizeEnum.EXTERNAL_FRAME_W.get(),
        DisplaySizeEnum.EXTERNAL_FRAME_H.get());

    ImageView img = new ImageView("iamges/conan.png");
    img.setFitWidth(DisplaySizeEnum.MENU_IMAGE_W.get());
    img.setFitHeight(DisplaySizeEnum.MENU_IMAGE_H.get());
    // Plus margin
    img.setX(DisplaySizeEnum.EXTERNAL_FRAME_W.get() - (img.getFitWidth() + 30));
    img.setY(DisplaySizeEnum.EXTERNAL_FRAME_H.get() - (img.getFitHeight() + 5));
    root.getChildren().add(img);

    root.getChildren().addAll(
        new MenuTitle(),
        new MenuBox(MenuItemEnum.createMenuItems())
    );
  }

}
