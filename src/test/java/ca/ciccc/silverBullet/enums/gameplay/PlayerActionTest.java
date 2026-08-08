package ca.ciccc.silverBullet.enums.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

class PlayerActionTest {

  @Test
  void mapsTheFivePlanningKeysToTheirActions() {
    assertEquals(PlayerAction.TURN_LEFT, PlayerAction.getActionByKeyCode(KeyCode.Q));
    assertEquals(PlayerAction.MOVE, PlayerAction.getActionByKeyCode(KeyCode.W));
    assertEquals(PlayerAction.TURN_RIGHT, PlayerAction.getActionByKeyCode(KeyCode.E));
    assertEquals(PlayerAction.SHOOT, PlayerAction.getActionByKeyCode(KeyCode.R));
    assertEquals(PlayerAction.WAIT, PlayerAction.getActionByKeyCode(KeyCode.T));
  }

  @Test
  void returnsNullForUnmappedLetterKey() {
    assertNull(PlayerAction.getActionByKeyCode(KeyCode.A));
  }

  @Test
  void returnsNullForSpaceWhichIsHandledSeparatelyAsEndTurn() {
    // SPACE ends the turn in GameScene; it is deliberately not an action.
    assertNull(PlayerAction.getActionByKeyCode(KeyCode.SPACE));
  }
}
