package ca.ciccc.silverBullet.enums.gameplay;

import javafx.scene.input.KeyCode;

public enum PlayerAction {
    TURN_LEFT,
    MOVE,
    TURN_RIGHT,
    SHOOT,
    WAIT,
    NONE;

    public static PlayerAction getActionByKeyCode(KeyCode key) {
        PlayerAction result = null;
        switch (key) {
            case Q:
                result = PlayerAction.TURN_LEFT;
                break;
            case W:
                result = PlayerAction.MOVE;
                break;
            case E:
                result = PlayerAction.TURN_RIGHT;
                break;
            case R:
                result = PlayerAction.SHOOT;
                break;
            case T:
                result = PlayerAction.WAIT;
                break;
        }
        return result;
    }
}
