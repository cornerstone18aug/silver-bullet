package ca.ciccc.silverBullet.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LevelFileReadUtilTest {

  @Test
  void everyBundledLevelLoadsAsANineByNineGrid() {
    for (int level = 1; level <= 3; level++) {
      char[][] map = LevelFileReadUtil.getLevelMapAry(level);
      assertEquals(9, map.length, "level " + level + " should have 9 rows");
      for (int row = 0; row < 9; row++) {
        assertEquals(9, map[row].length, "level " + level + " row " + row + " should have 9 columns");
      }
    }
  }

  @Test
  void wrapsTheBoardInEdgeTiles() {
    char[][] map = LevelFileReadUtil.getLevelMapAry(1);
    for (int i = 0; i < 9; i++) {
      assertEquals('E', map[0][i], "top row must be all edges");
      assertEquals('E', map[8][i], "bottom row must be all edges");
      assertEquals('E', map[i][0], "left column must be all edges");
      assertEquals('E', map[i][8], "right column must be all edges");
    }
  }

  @Test
  void parsesPlayerStartPositionsAtTheExpectedCoordinates() {
    // level1.txt places the four start markers just inside the corners.
    char[][] map = LevelFileReadUtil.getLevelMapAry(1);
    assertEquals('1', map[1][2]);
    assertEquals('4', map[1][6]);
    assertEquals('3', map[7][2]);
    assertEquals('2', map[7][6]);
  }

  @Test
  void throwsForANonExistentLevel() {
    assertThrows(RuntimeException.class, () -> LevelFileReadUtil.getLevelMapAry(0));
  }
}
