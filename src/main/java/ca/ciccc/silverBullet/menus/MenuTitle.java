package ca.ciccc.silverBullet.menus;

import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.ConstUtil.DisplaySizeEnum;
import ca.ciccc.silverBullet.utils.ConstUtil.FontSizeEnum;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * MenuTitle
 *
 * @author Masa
 */
public class MenuTitle extends StackPane {

  public MenuTitle() {
    Rectangle bg = new Rectangle(
        DisplaySizeEnum.TITLE_W.get(),
        DisplaySizeEnum.TITLE_H.get()
    );
    bg.setStroke(Color.WHITE);
    bg.setStrokeWidth(2);
    bg.setFill(null);

    Text text = new Text(ConstUtil.APP_NAME);
    text.setFill(Color.CADETBLUE);
    text.setFont(Font.font(
        ConstUtil.TITLE_FONT,
        FontWeight.SEMI_BOLD,
        FontSizeEnum.TITLE.get()
    ));

    this.setAlignment(Pos.CENTER);
    this.getChildren().addAll(bg, text);

    double xPosition = DisplaySizeEnum.EXTERNAL_FRAME_W.get() / 2 - DisplaySizeEnum.TITLE_W.get() / 2;
    this.setTranslateX(xPosition);
    this.setTranslateY(30);
  }
}
