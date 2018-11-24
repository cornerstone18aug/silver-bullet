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
<<<<<<< HEAD
    canShoot = true;
    image = new Rectangle(60, 60);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Water.jpg")));
=======
    image = new Rectangle(60, 60);
    ((Rectangle) image).setFill(new ImagePattern(fInput.image("file:src/main/resources/images/Tiles/Water.jpg")));
>>>>>>> juan_devBranch
  }
}
