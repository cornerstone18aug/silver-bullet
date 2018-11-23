package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.Player;
import javafx.scene.Node;

public abstract class GridNode {
    protected int gridX;
    protected int gridY;
    protected String name;

    protected double screenX;
    protected double screenY;

    protected boolean canMoveTo;

  protected boolean canShoot;

    protected Player playerInSpace = null;

    protected Node image;
    public Node squareNode;

    public boolean hasPlayer() {
        if(playerInSpace != null){
            return true;
        }
        return false;
    }

    public Player getPlayerInSpace() {
        return playerInSpace;
    }

    public void setPlayerInSpace(Player playerInSpace) {
        this.playerInSpace = playerInSpace;
    }


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

    public double getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public double getScreenY() {
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

  public boolean isCanShoot() {
    return canShoot;
  }

  public void setCanShoot(boolean canShoot) {
    this.canShoot = canShoot;
  }
}
