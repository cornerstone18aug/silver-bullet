package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Water extends GridNode {
  private final static String FILE_PATH = "file:src/main/resources/images/Tiles/TileRock%s.png";

  public Water(int gridx, int gridy) {
    MediaUtil fInput = new MediaUtil();
    this.gridX = gridx;
    this.gridY = gridy;

    name = "Water";
    canMoveTo = false;
    canShoot = true;
    image = new Rectangle(WIDTH, HEIGHT);
    int n = random.nextInt(3) + 1;
// <<<<<<< Hao-Tse/dev
// //    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Tiles/TileRock" + n + ".png")));
//     ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/Test/Wave.gif")));


// =======
    ((Rectangle) image).setFill(new ImagePattern(fInput.image(String.format(FILE_PATH, n))));
  }
}
