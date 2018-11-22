package ca.ciccc.silverBullet.components.menus;

import ca.ciccc.silverBullet.enums.MenuItemEnum;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.ConstUtil.DisplaySizeEnum;
import ca.ciccc.silverBullet.utils.ConstUtil.FontSizeEnum;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Each MenuItemEnum Item
 *
 * @author Masa
 */
public class MenuItem extends StackPane {

  public MenuItem(MenuItemEnum name) {
    Stop[] stops = new Stop[]{
        new Stop(0, Color.DARKBLUE),
        new Stop(0.1, Color.BLACK),
        new Stop(0.9, Color.BLACK),
        new Stop(1, Color.DARKBLUE)

    };
    LinearGradient gradient = new LinearGradient(
        0, 0, 1, 0,
        true,
        CycleMethod.NO_CYCLE,
        stops
    );

    Rectangle backGround = new Rectangle(
        DisplaySizeEnum.MENU_ITEM_W.get(),
        DisplaySizeEnum.MENU_ITEM_H.get());
    backGround.setOpacity(0.4);

    Text itemText = new Text(name.getName());
    itemText.setFill(Color.DARKGREY);
    itemText.setFont(
        Font.font(ConstUtil.MENU_FONT, FontWeight.SEMI_BOLD, FontSizeEnum.MENU.get())
    );

    this.setAlignment(Pos.CENTER);
    this.getChildren().addAll(backGround, itemText);
    this.setOnMouseEntered(event -> {
      backGround.setFill(gradient);
      itemText.setFill(Color.WHITE);
    });
    this.setOnMouseExited(event -> {
      backGround.setFill(Color.BLACK);
      itemText.setFill(Color.DARKGREY);
    });
    this.setOnMousePressed(event -> backGround.setFill(Color.DARKVIOLET));
    this.setOnMouseReleased(event -> backGround.setFill(gradient));

    this.setOnMouseClicked(name.event());
  }
}
