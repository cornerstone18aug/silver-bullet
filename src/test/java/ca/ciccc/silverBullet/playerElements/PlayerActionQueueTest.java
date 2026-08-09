package ca.ciccc.silverBullet.playerElements;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Behavioural tests for a player's 5-slot action queue
 * ({@code addAction} / {@code passTurn} / {@code resetActions}).
 */
class PlayerActionQueueTest {

  private static final int SLOTS = 5;

  @BeforeAll
  static void startJavaFx() throws InterruptedException {
    JavaFxToolkit.init();
  }

  private static Player newPlayer() {
    return new Player(3, 1, 0, 0, Directions.NORTH);
  }

  private static PlayerAction[] filled(PlayerAction... actions) {
    PlayerAction[] expected = new PlayerAction[SLOTS];
    for (int i = 0; i < SLOTS; i++) {
      expected[i] = i < actions.length ? actions[i] : PlayerAction.NONE;
    }
    return expected;
  }

  @Test
  void aFreshPlayerHasAnEmptyQueue() {
    Player player = newPlayer();
    assertArrayEquals(filled(), player.getPlayerActions());
    assertEquals(0, player.getCurrentAction());
    assertFalse(player.isActionsFull());
  }

  @Test
  void addActionFillsSlotsInOrder() {
    Player player = newPlayer();
    player.addAction(PlayerAction.MOVE);
    player.addAction(PlayerAction.TURN_LEFT);

    assertArrayEquals(filled(PlayerAction.MOVE, PlayerAction.TURN_LEFT), player.getPlayerActions());
    assertEquals(2, player.getCurrentAction());
    assertFalse(player.isActionsFull());
  }

  @Test
  void queueBecomesFullAndCurrentActionResetsAfterFiveActions() {
    Player player = newPlayer();
    for (int i = 0; i < SLOTS; i++) {
      player.addAction(PlayerAction.MOVE);
    }

    assertTrue(player.isActionsFull());
    assertEquals(0, player.getCurrentAction(), "currentAction wraps back to 0 once full");
    assertArrayEquals(
        filled(PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE),
        player.getPlayerActions());
  }

  @Test
  void addActionIsIgnoredOnceTheQueueIsFull() {
    Player player = newPlayer();
    for (int i = 0; i < SLOTS; i++) {
      player.addAction(PlayerAction.MOVE);
    }

    player.addAction(PlayerAction.SHOOT); // should be dropped

    assertTrue(player.isActionsFull());
    assertArrayEquals(
        filled(PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE),
        player.getPlayerActions());
  }

  @Test
  void passTurnPadsRemainingSlotsWithWait() {
    Player player = newPlayer();
    player.addAction(PlayerAction.MOVE);
    player.addAction(PlayerAction.SHOOT);

    player.passTurn();

    assertArrayEquals(
        filled(PlayerAction.MOVE, PlayerAction.SHOOT, PlayerAction.WAIT, PlayerAction.WAIT, PlayerAction.WAIT),
        player.getPlayerActions());
    assertTrue(player.isActionsFull());
  }

  @Test
  void passTurnOnAnEmptyQueueFillsItEntirelyWithWait() {
    Player player = newPlayer();

    player.passTurn();

    assertArrayEquals(
        filled(PlayerAction.WAIT, PlayerAction.WAIT, PlayerAction.WAIT, PlayerAction.WAIT, PlayerAction.WAIT),
        player.getPlayerActions());
    assertTrue(player.isActionsFull());
  }

  @Test
  void passTurnLeavesAnAlreadyFullQueueUnchanged() {
    Player player = newPlayer();
    for (int i = 0; i < SLOTS; i++) {
      player.addAction(PlayerAction.MOVE);
    }

    player.passTurn();

    assertArrayEquals(
        filled(PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE, PlayerAction.MOVE),
        player.getPlayerActions());
  }

  @Test
  void resetActionsClearsTheQueueAndTheFullFlag() {
    Player player = newPlayer();
    for (int i = 0; i < SLOTS; i++) {
      player.addAction(PlayerAction.MOVE);
    }

    player.resetActions();

    assertArrayEquals(filled(), player.getPlayerActions());
    assertFalse(player.isActionsFull());
  }
}
