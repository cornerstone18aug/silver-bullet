package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.gridNodes.Water;
import javafx.scene.layout.GridPane;

public class BackgroundGrid {

    GridPane gridBoard;
    GridNode[][] grid;
    private int size = 50;
    public static BackgroundGrid instance;


    BackgroundGrid() {
        generategrid();

        instance = this;
    }

    private void generategrid()
    {
        grid = new GridNode[size][size];
        gridBoard = new GridPane();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridNode nodeToAdd = new Water(j, i);
                gridBoard.add(nodeToAdd.getImage(), j, i);
                grid[i][j] = nodeToAdd;
                nodeToAdd.setGridX(j);
                nodeToAdd.setGridY(i);
            }
        }

        gridBoard.setTranslateX(0);
        gridBoard.setTranslateY(0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[j][i].setScreenX((i * 60) + 50);
                grid[j][i].setScreenY((j * 60) + 50);
            }
        }

    }
}
