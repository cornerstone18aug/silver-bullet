package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Space extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Tiles/Tile%s.png";

  public Space(int gridX, int gridY) {
    FileInput fInput = new FileInput();
    this.gridX = gridX;
    this.gridY = gridY;
    name = "Space";
    canMoveTo = true;
    canShoot = true;
    image = new Rectangle(WIDTH, HEIGHT);
    int n = random.nextInt(4) + 1;
    ((Rectangle) image).setFill(new ImagePattern(fInput.image(String.format(FILE_PATH, n))));
  }
}
