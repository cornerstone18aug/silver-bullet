package ca.ciccc.silverBullet.components.menus;

import ca.ciccc.silverBullet.utils.ConstUtil.DisplaySizeEnum;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Container for MenuItemEnum Item
 *
 * @author Masa
 */
public class MenuBox extends VBox {

  public MenuBox(MenuItem... items) {
    getChildren().add(createSeparator());

    for (MenuItem item : items) {
      getChildren().addAll(item, createSeparator());
    }

    double xPosition = DisplaySizeEnum.EXTERNAL_FRAME_W.get() / 2 - DisplaySizeEnum.MENU_ITEM_W.get() / 2;
    double yPosition = DisplaySizeEnum.EXTERNAL_FRAME_H.get() / 2 - (DisplaySizeEnum.MENU_ITEM_H.get() * items.length) / 2;
    this.setTranslateX(xPosition);
    this.setTranslateY(yPosition);
  }

  private Line createSeparator() {
    Line separateLine = new Line();
    separateLine.setEndX(DisplaySizeEnum.MENU_ITEM_W.get());
    separateLine.setStroke(Color.DARKGREY);

    return separateLine;
  }

}
