package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.gridNodes.Water;
import javafx.scene.layout.GridPane;

public class BackgroundGrid {

    GridPane gridPane = new GridPane();
    public static BackgroundGrid instance;

    BackgroundGrid() {
        int size = 50;
        GridNode[][] grid = new GridNode[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridNode nodeToAdd = new Water(j, i);
                this.gridPane.add(nodeToAdd.getImage(), j, i);
                grid[i][j] = nodeToAdd;
                nodeToAdd.setGridX(j);
                nodeToAdd.setGridY(i);
            }
        }

        this.gridPane.setTranslateX(0);
        this.gridPane.setTranslateY(0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[j][i].setScreenX((i * 60) + 50);
                grid[j][i].setScreenY((j * 60) + 50);
            }
        }

        instance = this;
    }
}
