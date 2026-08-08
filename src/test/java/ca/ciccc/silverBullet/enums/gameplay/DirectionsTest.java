package ca.ciccc.silverBullet.enums.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionsTest {

  // ---- movement deltas -----------------------------------------------------

  @Test
  void northStepsUpOneRow() {
    assertEquals(0, Directions.NORTH.dx());
    assertEquals(-1, Directions.NORTH.dy());
  }

  @Test
  void southStepsDownOneRow() {
    assertEquals(0, Directions.SOUTH.dx());
    assertEquals(1, Directions.SOUTH.dy());
  }

  @Test
  void eastStepsRightOneColumn() {
    assertEquals(1, Directions.EAST.dx());
    assertEquals(0, Directions.EAST.dy());
  }

  @Test
  void westStepsLeftOneColumn() {
    assertEquals(-1, Directions.WEST.dx());
    assertEquals(0, Directions.WEST.dy());
  }

  // ---- rotation ------------------------------------------------------------

  @Test
  void turningRightCyclesClockwise() {
    assertEquals(Directions.EAST, Directions.NORTH.rotate(Orientation.RIGHT));
    assertEquals(Directions.SOUTH, Directions.EAST.rotate(Orientation.RIGHT));
    assertEquals(Directions.WEST, Directions.SOUTH.rotate(Orientation.RIGHT));
    assertEquals(Directions.NORTH, Directions.WEST.rotate(Orientation.RIGHT));
  }

  @Test
  void turningLeftCyclesCounterClockwise() {
    assertEquals(Directions.WEST, Directions.NORTH.rotate(Orientation.LEFT));
    assertEquals(Directions.SOUTH, Directions.WEST.rotate(Orientation.LEFT));
    assertEquals(Directions.EAST, Directions.SOUTH.rotate(Orientation.LEFT));
    assertEquals(Directions.NORTH, Directions.EAST.rotate(Orientation.LEFT));
  }

  @Test
  void leftThenRightReturnsToStart() {
    for (Directions start : Directions.values()) {
      assertEquals(start, start.rotate(Orientation.LEFT).rotate(Orientation.RIGHT));
    }
  }

  @Test
  void fourRightTurnsReturnToStart() {
    for (Directions start : Directions.values()) {
      Directions d = start;
      for (int i = 0; i < 4; i++) {
        d = d.rotate(Orientation.RIGHT);
      }
      assertEquals(start, d);
    }
  }

  @Test
  void leftIsAlwaysTheInverseOfRight() {
    for (Directions start : Directions.values()) {
      assertEquals(start.rotate(Orientation.LEFT), start.rotate(Orientation.RIGHT).rotate(Orientation.RIGHT).rotate(Orientation.RIGHT));
    }
  }
}
