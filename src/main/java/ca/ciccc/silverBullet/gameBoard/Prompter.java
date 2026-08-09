package ca.ciccc.silverBullet.gameBoard;

/**
 * Seam for the confirmation dialogs that gate the turn flow. Production code
 * uses {@code ModalUtil::alertWithCallback}, which shows a modal window and runs
 * {@code onConfirm} when the user clicks OK. Tests can substitute an
 * implementation that invokes {@code onConfirm} immediately, so a full turn can
 * be driven without real dialogs.
 */
@FunctionalInterface
public interface Prompter {

  void prompt(String title, String message, Runnable onConfirm);
}
