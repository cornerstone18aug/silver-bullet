package ca.ciccc.silverBullet.testsupport;

import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;

/**
 * Test helper that starts the JavaFX toolkit once so tests can construct
 * scene-graph-backed game objects (players, action counters, the board).
 * Constructing those pulls in the graphics pipeline (fonts, effects), which
 * requires the toolkit to be initialized. In CI this runs under a virtual
 * display (xvfb).
 */
public final class JavaFxToolkit {

  private JavaFxToolkit() {
  }

  /** Idempotent: safe to call from every test class's {@code @BeforeAll}. */
  public static void init() throws InterruptedException {
    CountDownLatch ready = new CountDownLatch(1);
    try {
      Platform.startup(ready::countDown);
    } catch (IllegalStateException alreadyStarted) {
      // The toolkit is already up (another test started it); nothing to do.
      ready.countDown();
    }
    ready.await();
  }
}
