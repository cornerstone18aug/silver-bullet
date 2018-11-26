package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import ca.ciccc.silverBullet.playerElements.ActionCounter;
import ca.ciccc.silverBullet.playerElements.Player;
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

    public GameScene(int lvl) {
        backgroundGrid = new BackgroundGrid();
        gameBoard = new GridBoard(9, 9, lvl);
        instance = this;

        this.getChildren().add(backgroundGrid.gridBoard);
        this.getChildren().add(gameBoard.gridBoard);

        gameBoard.addPlayer(1, 1, 1);
        gameBoard.addPlayer(5, 5, 2);


        timerDisplay = new TimerDisplay(gameBoard.players);
        timerDisplay.setTranslateX(380);
        timerDisplay.setTranslateY(10);

        this.getChildren().add(timerDisplay);

        timerDisplay.setHighlight(0);

        for (int i = 0; i < gameBoard.players.size(); i++) {
            Player player = gameBoard.players.get(i);
            ActionCounter ac = player.getPlayerActionCounter();
            //ac.setTranslateX(175 + 200 * i);
      /* New value to center the board
      and the movements of the player */
            ac.setTranslateX(50 + 400 * i);
            ac.setTranslateY(590);
            this.getChildren().addAll(player.getPlayerNode(), ac);
        }
    }

    public GameScene() {

        gameBoard = new GridBoard(9, 9, 3);
        instance = this;
        backgroundGrid = new BackgroundGrid();

        this.getChildren().add(backgroundGrid.gridBoard);
        this.getChildren().add(gameBoard.gridBoard);


        gameBoard.addPlayer(1, 1, 1);
        gameBoard.addPlayer(5, 5, 2);


        for (int i = 0; i < gameBoard.players.size(); i++) {
            Player player = gameBoard.players.get(i);
            ActionCounter ac = player.getPlayerActionCounter();
            ac.setTranslateX(305 + 200 * i);
            ac.setTranslateY(630);
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
            GameScene gameScene = new GameScene(this.level);
            return gameScene;
        }

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

//    if (!gameBoard.areAllFull() &&
//        gameBoard.players.get(controllingPlayer).isActionsFull()) {
//      controllingPlayer++;
//    }

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
        ModalUtil.alertWithCallback("Planning Phase", "Move to planning phase?", () -> isPaused = false);
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


    private void turnEnd() {
        gameBoard.players.get(controllingPlayer).passTurn();
        isPaused = true;
        if (!gameBoard.areAllFull()) {
            controllingPlayer++;
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
            });
        }
    }
}

