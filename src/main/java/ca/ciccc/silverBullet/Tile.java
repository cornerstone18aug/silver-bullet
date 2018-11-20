package ca.ciccc.silverBullet;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Tile extends StackPane {

  private static final int TILE_SIZE = 40;
  private int x, y;
  private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);

  Tile(int x, int y) {
    this.x = x;
    this.y = y;

    border.setStroke(Color.LIGHTSKYBLUE);
    getChildren().addAll(border);

    setTranslateX(x * TILE_SIZE);
    setTranslateZ(y * TILE_SIZE);
  }

}
