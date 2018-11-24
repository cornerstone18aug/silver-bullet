package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Hole extends GridNode {

  public Hole(int gridx, int gridy) {
    FileInput fInput = new FileInput();
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Hole";
    canMoveTo = false;
    canShoot = true;
    image = new Rectangle(60, 60);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Hole.png")));
  }
}
