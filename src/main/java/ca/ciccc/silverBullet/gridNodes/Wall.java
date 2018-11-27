package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Wall extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Tiles/TileRock%s.png";

  public Wall(int gridx, int gridy) {
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Wall";
    canMoveTo = false;
    canShoot = false;
    image = new Rectangle(WIDTH, HEIGHT);
    ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format(FILE_PATH, 1))));
  }

}
