package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.playerElements.Player;

/**
 * Callbacks the board invokes on its owning scene when players are eliminated.
 * Injecting this (rather than the concrete {@code GameScene}) lets the board
 * notify the scene without a global singleton or a hard dependency on the
 * JavaFX view.
 */
public interface GameSceneEvents {

  /** Remove a knocked-out player's visuals (its board node and timer image). */
  void removePlayerVisuals(Player player);

  /** Announce the winner and show the game-over screen. */
  void showGameOver(int winningPlayerNumber);
}
