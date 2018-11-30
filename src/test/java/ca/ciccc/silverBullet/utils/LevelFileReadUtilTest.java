package ca.ciccc.silverBullet.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LevelFileReadUtilTest {

  @Test
  void getLevelMapAry() {
    assertEquals(9, LevelFileReadUtil.getLevelMapAry(1).length);
    assertEquals(9, LevelFileReadUtil.getLevelMapAry(2).length);
    assertEquals(9, LevelFileReadUtil.getLevelMapAry(3).length);

    assertThrows(RuntimeException.class,
        () -> LevelFileReadUtil.getLevelMapAry(0));
  }
}