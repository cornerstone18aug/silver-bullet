package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Space extends GridNode {

  public Space(int gridx, int gridy) {
    FileInput fInput = new FileInput();
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Space";
    canMoveTo = true;
<<<<<<< HEAD
    canShoot = true;
    image = new Rectangle(60, 60);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Tile1.png")));
=======
    image = new Rectangle(60, 60);
    int n = random.nextInt(4) + 1;
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Tiles/Tile" + n + ".png")));
>>>>>>> juan_devBranch
  }
}
