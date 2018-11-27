package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.utils.MediaUtil;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Edge extends GridNode {

    public Edge(int gridx, int gridy) {

    this.gridX = gridx;
    this.gridY = gridy;
    name = "Edge";
    canMoveTo = true;
    image = new Rectangle(60, 60);
    if (gridx == 0 && gridy != 0 && gridy != 8)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgesideL.png")));
    }
    else if (gridx == 8 && gridy != 0 && gridy != 8)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgesideR.png")));
    }
    else if (gridx != 0 && gridy == 0 && gridx != 8)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgesideU.png")));
    }
    else if (gridx != 0 && gridy == 8 && gridx != 8)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgesideD.png")));
    }
    else if(gridx == 0 && gridy == 0)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgeLU.png")));
    }
    else if(gridx == 0 && gridy == 8)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgeLD.png")));
    }
    else if(gridx == 8 && gridy == 0)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgeRU.png")));
    }
    else if(gridx == 8 && gridy == 8)
    {
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("file:src/main/resources/images/Tiles/Edges/TileEdgeRD.png")));

    }
}


  }

