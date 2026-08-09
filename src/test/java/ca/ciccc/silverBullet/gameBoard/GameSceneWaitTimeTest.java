package ca.ciccc.silverBullet.gameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ciccc.silverBullet.enums.gameplay.WaitTime;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** The configured wait time seeds the planning-phase countdown. */
class GameSceneWaitTimeTest {

  @BeforeAll
  static void startJavaFx() throws InterruptedException {
    JavaFxToolkit.init();
  }

  private static double buildAndReadTimer(Integer turnSeconds) throws InterruptedException {
    AtomicReference<GameScene> ref = new AtomicReference<>();
    JavaFxToolkit.runOnFxThread(() -> {
      GameScene.Builder builder = new GameScene.Builder().player(2).level(1);
      if (turnSeconds != null) {
        builder.turnSeconds(turnSeconds);
      }
      ref.set(builder.build());
    });
    return ref.get().getTurnTimer();
  }

  @Test
  void theCountdownStartsAtTheChosenWaitTime() throws InterruptedException {
    assertEquals(WaitTime.LONG.getSeconds(), buildAndReadTimer(WaitTime.LONG.getSeconds()));
    assertEquals(WaitTime.NORMAL.getSeconds(), buildAndReadTimer(WaitTime.NORMAL.getSeconds()));
  }

  @Test
  void itDefaultsToTenSecondsWhenUnset() throws InterruptedException {
    assertEquals(10.0, buildAndReadTimer(null));
  }
}
