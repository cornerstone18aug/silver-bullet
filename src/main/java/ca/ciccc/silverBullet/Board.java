package ca.ciccc.silverBullet;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

class Board {

  private static final int TILE_SIZE = 40;
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  private static final int X_TILES = WIDTH / TILE_SIZE;
  private static final int Y_TILES = HEIGHT / TILE_SIZE;

  private Tile[][] grid = new Tile[X_TILES][Y_TILES];

  Parent createContent() {
    Pane root = new Pane();
    root.setPrefSize(WIDTH, HEIGHT);

    for (int y = 0; y < Y_TILES; y++) {
      for (int x = 0; x < X_TILES; x++) {
        Tile tile = new Tile(x, y);
        grid[x][y] = tile;
        root.getChildren().add(tile);
      }
    }
    return root;
  }
}
