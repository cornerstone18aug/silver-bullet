package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Water extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Tiles/TileRock%s.png";

  public Water(int gridX, int gridY) {
    FileInput fInput = new FileInput();
    this.gridX = gridX;
    this.gridY = gridY;
    name = "Water";
    canMoveTo = false;
    canShoot = true;
    image = new Rectangle(WIDTH, HEIGHT);
    int n = random.nextInt(3) + 1;
    ((Rectangle) image).setFill(new ImagePattern(fInput.image(String.format(FILE_PATH, n))));
  }
}
