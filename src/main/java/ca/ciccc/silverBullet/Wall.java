package ca.ciccc.silverBullet;

public class Wall extends GridNode {

    public Wall(int gridx, int gridy) {
        this.gridX = gridx;
        this.gridY = gridy;

        name = "Wall";
        canMoveTo = false;
    }

}
