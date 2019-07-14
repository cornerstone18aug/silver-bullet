package ca.ciccc.silverBullet.gridNodes;

import ca.ciccc.silverBullet.playerElements.Player;
import java.util.Random;
import javafx.scene.Node;

public abstract class GridNode {

  static final int WIDTH = 60;
  static final int HEIGHT = 60;

  int gridX;
  int gridY;
  protected String name;

  private double screenX;
  private double screenY;

  boolean canMoveTo;

  boolean canShoot;

  boolean hasPickup;

  private Player playerInSpace = null;

  Random random = new Random();

  protected Node image;
  private Node pickupImage;

  int playerStartPosition = 0;

  public void setPickupImage(Node pickupImage) {
    this.pickupImage = pickupImage;
  }

  public Node getPickupImage() {
    return pickupImage;
  }

  public int getPlayerStartPosition() {
    return playerStartPosition;
  }

  public boolean hasPlayer() {
    return playerInSpace != null;
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

  public boolean isHasPickup() {
    return hasPickup;
  }

  public void setHasPickup(boolean hasPickup) {
    this.hasPickup = hasPickup;
  }
}
