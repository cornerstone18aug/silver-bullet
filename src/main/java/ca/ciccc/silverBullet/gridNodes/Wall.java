package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Wall extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Tiles/TileRock%s.png";

  public Wall(int gridX, int gridY) {
    FileInput fInput = new FileInput();
    this.gridX = gridX;
    this.gridY = gridY;
    name = "Wall";
    canMoveTo = false;
    canShoot = false;
    image = new Rectangle(WIDTH, HEIGHT);
//    int n = random.nextInt(3) + 1;
    ((Rectangle) image).setFill(new ImagePattern(fInput.image(String.format(FILE_PATH, n))));
  }

}
