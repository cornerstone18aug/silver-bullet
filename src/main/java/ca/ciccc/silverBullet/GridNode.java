package ca.ciccc.silverBullet;

import javafx.scene.Node;

public abstract class GridNode {
    protected int gridX;
    protected int gridY;
    protected String name;

    protected int screenX;
    protected int screenY;

    protected boolean canMoveTo;

    protected boolean hasPlayer;

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public Player getPlayerInSpace() {
        return playerInSpace;
    }

    public void setPlayerInSpace(Player playerInSpace) {
        this.playerInSpace = playerInSpace;
    }

    protected Player playerInSpace;

    protected Node image;

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public Node getImage() {
        return image;
    }

    public void setImage(Node image) {
        this.image = image;
    }

    public boolean isCanMoveTo() {
        return canMoveTo;
    }
}
