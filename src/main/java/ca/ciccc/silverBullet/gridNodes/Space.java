package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Space extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Tiles/Tile%s.png";

  public Space(int gridx, int gridy, boolean hasPickup) {
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Space";
    canMoveTo = true;
    canShoot = true;
    this.hasPickup = hasPickup;
    image = new Rectangle(WIDTH, HEIGHT);
    int n = random.nextInt(4) + 1;
    ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format(FILE_PATH, n))));
  }
}
