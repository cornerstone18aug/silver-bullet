package ca.ciccc.silverBullet.enums.gameplay;

import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.gridNodes.Hole;
import ca.ciccc.silverBullet.gridNodes.Space;
import ca.ciccc.silverBullet.gridNodes.Wall;
import ca.ciccc.silverBullet.gridNodes.Water;
import java.util.Arrays;
import java.util.Optional;

public enum GridElement {
  HOLE('H'),
  WALL('W'),
  SPACE('S'),
  WATER('E');

  private char letter;

  GridElement(char letter) {
    this.letter = letter;
  }

  public static GridNode createGridNode(char letter, int positionX, int positionY) {
    Optional<GridElement> gridElementOpt = Arrays.stream(values())
        .filter(gridElement -> gridElement.letter == letter)
        .findFirst();
    switch (gridElementOpt.orElseThrow(IllegalStateException::new)) {
      case HOLE:
        return new Hole(positionX, positionY);
      case WALL:
        return new Wall(positionX, positionY);
      case SPACE:
        return new Space(positionX, positionY);
      case WATER:
        return new Water(positionX, positionY);
    }
    throw new IllegalStateException();
  }

}
