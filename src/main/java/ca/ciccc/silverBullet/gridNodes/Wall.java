package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Wall extends GridNode {

  public Wall(int gridx, int gridy) {
    FileInput fInput = new FileInput();
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Wall";
    canMoveTo = false;
    canShoot = false;
    image = new Rectangle(60, 60);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/TileRock1.png")));
  }

}
