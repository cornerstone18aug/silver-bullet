package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.controller.GameController;
import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.playerElements.ActionCounter;
import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.extraScreens.GameOverScreen;
import ca.ciccc.silverBullet.utils.ConstUtil.GameSceneCoordinatesEnum;
import ca.ciccc.silverBullet.utils.ModalUtil;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GameScene extends Pane {

  private GridBoard gameBoard;
  private BackgroundGrid backgroundGrid;
  private double t = 0;
  private boolean isExecuting;
  private int currentActionNumber = 0;
  private int controllingPlayer = 0;
  static GameScene instance;
  private double turnTimer = 10;
  TimerDisplay timerDisplay;
  boolean isPaused;

  GameOverScreen gameOverScreen;

  public GameScene(int lvl, int numberOfPlayers) {
    backgroundGrid = new BackgroundGrid();
    gameBoard = new GridBoard(GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), lvl);
    instance = this;

    this.getChildren().add(backgroundGrid.gridBoard);
    this.getChildren().add(gameBoard.gridBoard);


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

  public GameScene() {

    gameBoard = new GridBoard(GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), GameSceneCoordinatesEnum.LEVEL_SELECTED.get());
    instance = this;
    backgroundGrid = new BackgroundGrid();

    this.getChildren().add(backgroundGrid.gridBoard);
    this.getChildren().add(gameBoard.gridBoard);

    gameBoard.addPlayer(1, 1, 1);
    gameBoard.addPlayer(5, 5, 2);

    gameBoard.addPlayer(GameSceneCoordinatesEnum.POSITION_PLAYER_1_X.get(), GameSceneCoordinatesEnum.POSITION_PLAYER_1_Y.get(), GameSceneCoordinatesEnum.PLAYER_NUMBER_1.get());
    gameBoard.addPlayer(GameSceneCoordinatesEnum.POSITION_PLAYER_2_X.get(), GameSceneCoordinatesEnum.POSITION_PLAYER_2_Y.get(), GameSceneCoordinatesEnum.PLAYER_NUMBER_2.get());

    for (int i = 0; i < gameBoard.players.size(); i++) {
      Player player = gameBoard.players.get(i);
      ActionCounter ac = player.getPlayerActionCounter();
      ac.setTranslateX(GameSceneCoordinatesEnum.SIZE_BOARD_X.get() + 200 * i);
      ac.setTranslateY(GameSceneCoordinatesEnum.SIZE_BOARD_Y.get());
      this.getChildren().addAll(player.getPlayerNode(), ac);
    }

  }

  public static class Builder {
    private int playerNumber;
    private int boardSize;
    private int level;

    public Builder player(int playerNumber) {
      this.playerNumber = playerNumber;
      return this;
    }

    public Builder boardSize(int boardSize) {
      this.boardSize = boardSize;
      return this;
    }

    public Builder level(int level) {
      this.level = level;
      return this;
    }

    public GameScene build() {
      GameScene gameScene = new GameScene(this.level, playerNumber);
      return gameScene;
    }

  }


  public void showGameOver(int playerWhoWon){
    stopAll();
    gameOverScreen = new GameOverScreen(playerWhoWon);
    this.getChildren().add(gameOverScreen);
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

  public void executePlayerActions() {

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

  public void actionEndStep() {

    for (Player p : gameBoard.players) {
      p.getPlayerActionCounter().clearActions();
      p.resetActions();
    }

    isExecuting = false;
    controllingPlayer = 0;
    currentActionNumber = 0;
    isPaused = true;
    timerDisplay.setHighlight(controllingPlayer);
    ModalUtil.alertWithCallback("Planning Phase", "Move to planning phase?", () -> {isPaused = false;
      highlightActions(gameBoard.players.get(controllingPlayer));
    });
  }

  public void stopAll(){
    GameController.getInstance().timer.stop();
  }

  private void executeMove() {
    gameBoard.players.forEach(player -> {
      if (gameBoard.players.stream().anyMatch(p -> {
        if (!p.equals(player) && player.getTargetMove() != null) {
          return !(p.getTargetMove() != null
              && p.getTargetMove().equals(player.getTargetMove()));
        } else if (player.getTargetMove() == null) {
          return false;
        }
        return false;
      })) {
        gameBoard.movePlayer(player);
      }
    });

  }

  public void highlightActions(Player playerToHighlight){
    gameBoard.players.forEach(p->{
      if(!playerToHighlight.equals(p)){
        p.getPlayerActionCounter().darkenSelf();
      } else{
        p.getPlayerActionCounter().lightenSelf();
      }
    });
  }

  public void highlightAllActions(){
    gameBoard.players.forEach(p->p.getPlayerActionCounter().lightenSelf());
  }

  private void turnEnd() {
    gameBoard.players.get(controllingPlayer).passTurn();
    isPaused = true;
    if (!gameBoard.areAllFull()) {
      controllingPlayer++;
      highlightActions(gameBoard.players.get(controllingPlayer));
      timerDisplay.setHighlight(controllingPlayer);
      ModalUtil.alertWithCallback("Next Turn", "Next Player's Turn", () -> {
        isPaused = false;
        turnTimer = 10;
      });


    } else {
      timerDisplay.highlightAll();
      ModalUtil.alertWithCallback("Execute", "Move to execution?", () -> {
        turnTimer = 10;
        currentActionNumber = 0;
        controllingPlayer = 0;
        isExecuting = true;
        isPaused = false;
        highlightAllActions();
      });
    }
  }
}
