package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.controller.GameController;
import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.extraScreens.GameOverScreen;
import ca.ciccc.silverBullet.extraScreens.InstructionStuff;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.logic.GameLogic;
import ca.ciccc.silverBullet.playerElements.ActionCounter;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.utils.ConstUtil.GameSceneCoordinatesEnum;
import ca.ciccc.silverBullet.utils.ModalUtil;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GameScene extends Pane implements GameSceneEvents {

  private GridBoard gameBoard;
  private double t = 0;
  private boolean isExecuting;
  private int currentActionNumber = 0;
  private int controllingPlayer = 0;
  private final int turnDuration;
  private double turnTimer;
  TimerDisplay timerDisplay;
  private boolean isPaused;
  private Prompter prompter = ModalUtil::alertWithCallback;

  private GameScene(int lvl, int numberOfPlayers, int turnSeconds) {
    this.turnDuration = turnSeconds;
    this.turnTimer = turnSeconds;
    BackgroundGrid backgroundGrid = new BackgroundGrid();
    gameBoard = new GridBoard(GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), lvl);
    gameBoard.setSceneEvents(this);

    this.getChildren().add(backgroundGrid.gridPane);
    this.getChildren().add(gameBoard.gridBoard);


    Pane instructionsPane = new InstructionStuff();
    this.getChildren().add(instructionsPane);

    for(int i = 1; i < numberOfPlayers+1; i++){
      GridNode playerNode = gameBoard.getPlayerStartLocation()[i-1];

      gameBoard.addPlayer(playerNode.getGridX(), playerNode.getGridY(), i);
    }

    timerDisplay = new TimerDisplay(gameBoard.players);

    if(numberOfPlayers == 4){
      timerDisplay.setTranslateX(GameSceneCoordinatesEnum.TIMER_DISPLAY_X.get()- 40);
    }else{
      timerDisplay.setTranslateX(GameSceneCoordinatesEnum.TIMER_DISPLAY_X.get());
    }


    timerDisplay.setTranslateY(GameSceneCoordinatesEnum.TIMER_DISPLAY_Y.get());

    this.getChildren().add(timerDisplay);

    timerDisplay.setHighlight(0);

    for (int i = 0; i < gameBoard.players.size(); i++) {
      Player player = gameBoard.players.get(i);
      ActionCounter ac = player.getPlayerActionCounter();

      ac.adjustActionCounter(gameBoard.players.size(), i);

      ac.setTranslateY(GameSceneCoordinatesEnum.SIZE_BOARD_Y_MAIN.get());
      this.getChildren().addAll(player.getPlayerNode(), ac);
    }

    highlightActions(gameBoard.players.get(0));


  }

  public static class Builder {
    private int playerNumber;
    private int level;
    private int turnSeconds = 10;

    public Builder player(int playerNumber) {
      this.playerNumber = playerNumber;
      return this;
    }

    public Builder level(int level) {
      this.level = level;
      return this;
    }

    public Builder turnSeconds(int turnSeconds) {
      this.turnSeconds = turnSeconds;
      return this;
    }

    public GameScene build() {
      return new GameScene(this.level, playerNumber, turnSeconds);
    }

  }

  @Override
  public void removePlayerVisuals(Player player) {
    this.getChildren().remove(player.getPlayerNode());
    timerDisplay.removePlayerImage(player);
  }

  @Override
  public void showGameOver(int playerWhoWon){
    stopAll();
    GameOverScreen gameOverScreen = new GameOverScreen(playerWhoWon);
    this.getChildren().add(gameOverScreen);
  }

  // Package-private accessor so the turn/planning flow can be smoke-tested.
  GridBoard getGameBoard() {
    return gameBoard;
  }

  // Package-private accessor exposing the countdown for tests.
  double getTurnTimer() {
    return turnTimer;
  }

  // Package-private seam so tests can auto-confirm the turn-flow dialogs.
  void setPrompter(Prompter prompter) {
    this.prompter = prompter;
  }

  public void boardUpdate() {
    if (!isExecuting && !isPaused) {

      if (turnTimer <= 0) {
        isPaused = true;
        turnEnd();
      } else {
        turnTimer -= 0.016;
        timerDisplay.timerUpdate(turnTimer);
      }

    } else if (isExecuting) {

      if (t <= 0) {
        t = .4;
        executePlayerActions();
        currentActionNumber++;

        if (currentActionNumber > 4) {
          actionEndStep();
        }
      } else {
        t -= 0.016;
      }

    }


  }

  public void onKeyPressed(KeyCode key) {
    if (isExecuting) {
      return;
    }

    if (!gameBoard.areAllFull()) {
      gameBoard.players
          .get(controllingPlayer)
          .addAction(PlayerAction.getActionByKeyCode(key));

    }

    if (KeyCode.SPACE.equals(key)) {
      turnEnd();
    }

  }

  private void executePlayerActions() {

    for (Player p : gameBoard.players) {
      if (PlayerAction.SHOOT.equals(p.getPlayerActions()[currentActionNumber])) {
        p.takeAction(currentActionNumber);
      }
    }

    for (Player p : gameBoard.players) {
      if (!PlayerAction.SHOOT.equals(p.getPlayerActions()[currentActionNumber])) {
        p.takeAction(currentActionNumber);
      }
    }

    executeMove();
  }

  private void actionEndStep() {

    for (Player p : gameBoard.players) {
      p.getPlayerActionCounter().clearActions();
      p.resetActions();
    }

    isExecuting = false;
    controllingPlayer = 0;
    currentActionNumber = 0;
    isPaused = true;
    timerDisplay.setHighlight(controllingPlayer);
    prompter.prompt("Planning Phase", "Move to planning phase?", () -> {isPaused = false;
      highlightActions(gameBoard.players.get(controllingPlayer));
    });
  }

  private void stopAll(){
    // The game loop may not be running (e.g. game-over reached outside a live
    // session); only stop it when there is a timer to stop.
    if (GameController.getInstance().timer != null) {
      GameController.getInstance().timer.stop();
    }
  }

  // Package-private so simultaneous-move collision resolution can be tested.
  void executeMove() {
    // Snapshot every player's destination, decide all moves at once, then apply
    // them. Players contending for the same tile collide and none of them move.
    int[][] targets = new int[gameBoard.players.size()][];
    for (int i = 0; i < gameBoard.players.size(); i++) {
      Move move = gameBoard.players.get(i).getTargetMove();
      targets[i] = move == null ? null : new int[] {move.getMoveX(), move.getMoveY()};
    }

    boolean[] canMove = GameLogic.resolveSimultaneousMoves(targets);
    for (int i = 0; i < gameBoard.players.size(); i++) {
      if (canMove[i]) {
        gameBoard.movePlayer(gameBoard.players.get(i));
      }
    }

    // A move applies only in its own step. Drop any target left unfulfilled by a
    // collision so it cannot resolve on a later, non-move step. (Players that
    // moved were already cleared by movePlayer.)
    gameBoard.players.forEach(player -> player.setTargetMove(null));
  }

  private void highlightActions(Player playerToHighlight){
    gameBoard.players.forEach(p->{
      if(!playerToHighlight.equals(p)){
        p.getPlayerActionCounter().darkenSelf();
      } else{
        p.getPlayerActionCounter().lightenSelf();
      }
    });
  }

  private void highlightAllActions(){
    gameBoard.players.forEach(p->p.getPlayerActionCounter().lightenSelf());
  }

  private void turnEnd() {
    gameBoard.players.get(controllingPlayer).passTurn();
    isPaused = true;
    if (!gameBoard.areAllFull()) {
      controllingPlayer++;
      highlightActions(gameBoard.players.get(controllingPlayer));
      timerDisplay.setHighlight(controllingPlayer);
      prompter.prompt("Next Turn", "Next Player's Turn", () -> {
        isPaused = false;
        turnTimer = turnDuration;
      });


    } else {
      timerDisplay.highlightAll();
      prompter.prompt("Execute", "Move to execution?", () -> {
        turnTimer = turnDuration;
        currentActionNumber = 0;
        controllingPlayer = 0;
        isExecuting = true;
        isPaused = false;
        highlightAllActions();
      });
    }
  }
}
