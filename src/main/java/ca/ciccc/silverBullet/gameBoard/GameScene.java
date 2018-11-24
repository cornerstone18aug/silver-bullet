package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.enums.gameplay.PlayerAction;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GameScene extends Pane {

    private GridBoard gameBoard;
    private double t = 0;
    private boolean isExecuting;
    private int currentActionNumber = 0;
    private int controllingPlayer = 0;
    static GameScene instance;


    public GameScene() {
        gameBoard = new GridBoard(9, 9,3);
        instance = this;

        this.getChildren().add(gameBoard.gridBoard);

        gameBoard.addPlayer(1, 1, 1);
        gameBoard.addPlayer(5, 5, 2);

        for (int i = 0; i < gameBoard.players.size(); i++){

            gameBoard.players.get(i).getPlayerActionCounter().setTranslateX(175 + 200 * i);
            gameBoard.players.get(i).getPlayerActionCounter().setTranslateY(630);

            this.getChildren().addAll(gameBoard.players.get(i).getPlayerNode(),
                    gameBoard.players.get(i).getPlayerActionCounter());
        }


    }

    public void boardUpdate(){
        if(isExecuting){
            if(t <= 0){

                t = .4;

                executePlayerActions();
                currentActionNumber++;

                if(currentActionNumber>4){

                    actionEndStep();

                }
            } else {
                t -= 0.016;
            }
        }

    }

    public void onKeyPressed(KeyCode key){
        if(!isExecuting){

            if(!gameBoard.areAllFull()){

                switch (key) {
                    case Q:
                        gameBoard.players.get(controllingPlayer).addAction(PlayerAction.TURN_LEFT);
                        break;
                    case W:
                        gameBoard.players.get(controllingPlayer).addAction(PlayerAction.MOVE);
                        break;
                    case E:
                        gameBoard.players.get(controllingPlayer).addAction(PlayerAction.TURN_RIGHT);

                        break;
                    case R:
                        gameBoard.players.get(controllingPlayer).addAction(PlayerAction.SHOOT);

                        break;
                    case T:
                        gameBoard.players.get(controllingPlayer).addAction(PlayerAction.WAIT);
                        break;
                }
            }
            if(!gameBoard.areAllFull() && gameBoard.players.get(controllingPlayer).isActionsFull()){
                controllingPlayer++;
            }
            if(KeyCode.SPACE.equals(key)){
                currentActionNumber  = 0;
                controllingPlayer = 0;
                isExecuting = true;

            }
        }
    }

    public void executePlayerActions(){

        for(Player p : gameBoard.players){
            if(p.getPlayerActions()[currentActionNumber].equals(PlayerAction.SHOOT)){
                p.takeAction(currentActionNumber);
            }
        }

        for(Player p : gameBoard.players){
            if(!p.getPlayerActions()[currentActionNumber].equals(PlayerAction.SHOOT)){
                p.takeAction(currentActionNumber);
            }
        }

        executeMove();


    }

    public void actionEndStep(){

        for(Player p : gameBoard.players){
            p.getPlayerActionCounter().clearActions();
            p.resetActions();
        }

        isExecuting = false;
        controllingPlayer = 0;
        currentActionNumber = 0;
    }

    private void executeMove(){
        gameBoard.players.forEach(player -> {
            if (gameBoard.players.stream().anyMatch(p->{
                if(!p.equals(player) && player.getTargetMove()!= null){
                   return  !(p.getTargetMove() != null && p.getTargetMove().equals(player.getTargetMove()));
                } else if(player.getTargetMove() == null){
                    return false;
                }
                return false;
            })){
                gameBoard.movePlayer(player);
            }
        });

    }


}
