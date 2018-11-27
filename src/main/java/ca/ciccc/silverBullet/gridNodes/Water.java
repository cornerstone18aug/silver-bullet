package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Water extends GridNode {

  private final static String FILE_PATH = "file:src/main/resources/images/Tiles/TileRock%s.png";

  public Water(int gridx, int gridy) {
    this.gridX = gridx;
    this.gridY = gridy;

    name = "Water";
    canMoveTo = false;
    canShoot = true;
    image = new Rectangle(WIDTH, HEIGHT);
    int n = random.nextInt(3) + 1;
    ((Rectangle) image)
        .setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/Test/Wave.gif")));
  }
}
