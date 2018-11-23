package ca.ciccc.silverBullet.gridNodes;

import FileInput.FileInput;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Space extends GridNode {

  public Space(int gridx, int gridy) {
    FileInput fInput = new FileInput();
    this.gridX = gridx;
    this.gridY = gridy;
    name = "Space";
    canMoveTo = true;
    image = new Rectangle(50, 50);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Tile1.png")));
  }
}
