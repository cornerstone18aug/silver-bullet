package ca.ciccc.silverBullet;

public class Space extends GridNode{



    public Space(int gridx, int gridy) {
        this.gridX = gridx;
        this.gridY = gridy;

        name = "Space";
        canMoveTo = true;
        hasPlayer = false;
    }
}
