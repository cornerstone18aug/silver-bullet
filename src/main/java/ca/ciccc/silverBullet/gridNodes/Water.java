package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Water extends GridNode {

  public Water(int gridx, int gridy) {
    FileInput fInput = new FileInput();
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Water";
    canMoveTo = false;
    canShoot = true;
    image = new Rectangle(60, 60);
    int n = random.nextInt(3) + 1;
//    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Tiles/TileRock" + n + ".png")));
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/Test/Wave.gif")));


  }
}
