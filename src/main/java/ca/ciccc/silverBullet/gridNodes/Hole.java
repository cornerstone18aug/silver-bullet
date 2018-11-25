package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Hole extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Hole.png";

  public Hole(int gridX, int gridY) {
    FileInput fInput = new FileInput();
    this.gridX = gridX;
    this.gridY = gridY;
    name = "Hole";
    canMoveTo = false;
    canShoot = true;
    image = new Rectangle(WIDTH, HEIGHT);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image(FILE_PATH)));
  }
}
