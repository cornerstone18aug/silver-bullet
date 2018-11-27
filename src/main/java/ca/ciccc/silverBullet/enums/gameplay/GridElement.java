package ca.ciccc.silverBullet.enums.gameplay;

import ca.ciccc.silverBullet.gridNodes.Edge;
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
  EDGE('E'),
  PICKUP('P'),
  ONE('1'),
  TWO('2'),
  THREE('3'),
  FOUR('4');

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
        return new Space(positionX, positionY, false, 0);
      case PICKUP:
        return new Space(positionX,positionY, true, 0);
      case EDGE:
        return new Edge(positionX, positionY, false);
      case ONE:
      case TWO:
      case THREE:
      case FOUR:
        return new Space(positionX, positionY, false, Character.getNumericValue(letter));
    }
    throw new IllegalStateException();
  }

}
