package ca.ciccc.silverBullet.enums.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WaitTimeTest {

  @Test
  void eachOptionMapsToItsSeconds() {
    assertEquals(10, WaitTime.SHORT.getSeconds());
    assertEquals(20, WaitTime.NORMAL.getSeconds());
    assertEquals(30, WaitTime.LONG.getSeconds());
  }

  @Test
  void labelsIncludeTheDurationForTheComboBox() {
    assertEquals("Short (10s)", WaitTime.SHORT.toString());
    assertEquals("Normal (20s)", WaitTime.NORMAL.toString());
    assertEquals("Long (30s)", WaitTime.LONG.toString());
  }

  @Test
  void theDefaultIsNormal() {
    assertEquals(WaitTime.NORMAL, WaitTime.DEFAULT);
  }
}
