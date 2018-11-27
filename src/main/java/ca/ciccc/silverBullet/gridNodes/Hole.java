package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Hole extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Hole.png";

  public Hole(int gridx, int gridy) {
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Hole";
    canMoveTo = false;
    canShoot = true;
    image = new Rectangle(WIDTH, HEIGHT);
    ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(FILE_PATH)));
  }
}
