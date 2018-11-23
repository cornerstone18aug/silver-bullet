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
    image = new Rectangle(50, 50);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Water.jpg")));
  }
}
