package ca.ciccc.silverBullet.playerElements;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
import ca.ciccc.silverBullet.gameBoard.Move;
import ca.ciccc.silverBullet.gridNodes.GridNode;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bullet extends Rectangle {

    Player playerShooting;
    private final GridBoard board;

    public Bullet(Move startPosition, Move endPosition, Player player, GridBoard board) {
        super(25, 25, 50, 50);
        playerShooting = player;
        this.board = board;

        GridNode startNode = board.getNodeFromGrid(startPosition.getMoveX(), startPosition.getMoveY());
        GridNode endNode = board.getNodeFromGrid(endPosition.getMoveX(), endPosition.getMoveY());
        setTranslateX(startNode.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 10);
        setTranslateY(startNode.getScreenY() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_Y.get());
        shootMovement(startNode, endNode, player);
    }

    public void shootMovement(GridNode startPos, GridNode endPos, Player player) {
        TranslateTransition transition = new TranslateTransition();

        switch (player.getPlayerNumber()) {
            case 1:
                this.setFill(new ImagePattern(MediaUtil.createImage("/images/Character/Fire/FireAttack.png")));
                break;
            case 2:
                this.setFill(new ImagePattern(MediaUtil.createImage("/images/Character/Rock/RockAttack.png")));
                break;
            default:
                this.setFill(new ImagePattern(MediaUtil.createImage("/images/Character/Fire/FireAttack.png")));
                break;
            case 3:
                this.setFill(new ImagePattern(MediaUtil.createImage("/images/Character/Water/WaterAttack.png")));
                break;
            case 4:
                this.setFill(new ImagePattern(MediaUtil.createImage("/images/Character/Wind/WindAttack.png")));
                break;
        }

        if (player.getFacingDirection().equals(Directions.SOUTH)
                || player.getFacingDirection().equals(Directions.NORTH)) {

            transition.setFromX(startPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);
            transition.setToX(endPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);

        } else {
            transition.setFromX(startPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);
            transition.setToX(endPos.getScreenX() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_X.get() + 7);
        }

        transition.setFromY(startPos.getScreenY() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_Y.get());
        transition.setInterpolator(Interpolator.EASE_IN);
        transition.setOnFinished(e -> onBulletStop());

        transition.setDuration(Duration.seconds(.5));
        transition.setToY(endPos.getScreenY() - ConstUtil.GridBoardSizeEnum.BOARD_POSITION_Y.get());
        transition.setNode(this);
        transition.play();
    }

    public void onBulletStop() {
        board.gridBoard.getChildren().remove(this);
    }
}
