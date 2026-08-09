package ca.ciccc.silverBullet.enums.gameplay;

/**
 * Selectable planning-phase durations — how long each player has to enter their
 * commands before the turn passes on. Chosen on the settings screen.
 */
public enum WaitTime {
  SHORT("Short", 10),
  NORMAL("Normal", 20),
  LONG("Long", 30);

  /** Pre-selected default on the settings screen. */
  public static final WaitTime DEFAULT = NORMAL;

  private final String label;
  private final int seconds;

  WaitTime(String label, int seconds) {
    this.label = label;
    this.seconds = seconds;
  }

  public int getSeconds() {
    return seconds;
  }

  /** Shown in the settings combo box, e.g. {@code "Normal (20s)"}. */
  @Override
  public String toString() {
    return String.format("%s (%ds)", label, seconds);
  }
}
