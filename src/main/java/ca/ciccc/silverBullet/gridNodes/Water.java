package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Water extends GridNode {

  public Water(int gridx, int gridy) {
    this.gridX = gridx;
    this.gridY = gridy;

    this.name = "Water";
    this.canMoveTo = false;
    this.canShoot = true;
    this.image = new Rectangle(WIDTH, HEIGHT);
    ((Rectangle) image)
        .setFill(new ImagePattern(MediaUtil.createImage("/images/Wave.gif")));
  }
}
