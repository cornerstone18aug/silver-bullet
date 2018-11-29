package ca.ciccc.silverBullet.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelFileReadUtilTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getLevelMapAry() {
    System.out.println(LevelFileReadUtil.getLevelMapAry(1));
    assertTrue(true);
  }
}