package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Edge extends GridNode {

  public Edge(int gridx, int gridy, boolean hasPickup) {

    this.gridX = gridx;
    this.gridY = gridy;
    name = "Edge";
    canMoveTo = true;
    this.hasPickup = hasPickup;
    image = new Rectangle(60, 60);
    if (gridx == 0 && gridy != 0 && gridy != 8) {
      ((Rectangle) image).setFill(
          new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgesideL.png")));
    } else if (gridx == 8 && gridy != 0 && gridy != 8) {
      ((Rectangle) image).setFill(
          new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgesideR.png")));
    } else if (gridx != 0 && gridy == 0 && gridx != 8) {
      ((Rectangle) image).setFill(
          new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgesideU.png")));
    } else if (gridx != 0 && gridy == 8 && gridx != 8) {
      ((Rectangle) image).setFill(
          new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgesideD.png")));
    } else if (gridx == 0 && gridy == 0) {
      ((Rectangle) image)
          .setFill(new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgeLU.png")));
    } else if (gridx == 0 && gridy == 8) {
      ((Rectangle) image)
          .setFill(new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgeLD.png")));
    } else if (gridx == 8 && gridy == 0) {
      ((Rectangle) image)
          .setFill(new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgeRU.png")));
    } else if (gridx == 8 && gridy == 8) {
      ((Rectangle) image)
          .setFill(new ImagePattern(MediaUtil.createImage("/images/Tiles/Edges/TileEdgeRD.png")));

    }
  }


}

