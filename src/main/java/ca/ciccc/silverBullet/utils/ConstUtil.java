package ca.ciccc.silverBullet.utils;

import java.util.ResourceBundle;

public class ConstUtil {

  private static ResourceBundle RESOURCE_BUNDLE = null;

  public static void setResourceBundle(ResourceBundle resourceBundle) {
    if (RESOURCE_BUNDLE != null) {
      throw new IllegalStateException("Already set");
    }
    RESOURCE_BUNDLE = resourceBundle;
  }

  private ConstUtil() {
  }

  public static String getRbString(String key) {
    return RESOURCE_BUNDLE.getString(key);
  }

  public interface hasDoubleSize {
    double get();
  }

  public interface hasIntSize {
    int get();
  }

  public enum DisplaySizeEnum implements hasDoubleSize {
    EXTERNAL_FRAME_W(900),
    EXTERNAL_FRAME_H(600),
    TITLE_W(EXTERNAL_FRAME_W.get()),
    TITLE_H(80),
    MENU_ITEM_W(300),
    MENU_ITEM_H(80),
    MENU_IMAGE_W(180),
    MENU_IMAGE_H(450),
    MODAL_MIN_W(200),
    MODAL_MIN_H(100);

    double size;

    DisplaySizeEnum(double size) {
      this.size = size;
    }

    public double get() {
      return this.size;
    }
  }

  public enum FontSizeEnum implements hasDoubleSize {
    TITLE(100),
    MENU(DisplaySizeEnum.MENU_ITEM_H.get() / 2);

    double size;

    FontSizeEnum(double size) {
      this.size = size;
    }

    public double get() {
      return this.size;
    }
  }

  public enum GridBoardSizeEnum implements hasIntSize {
    BOARD_POSITION_X(182),
    BOARD_POSITION_Y(50),
    TILE_SIZE(60),
    SPACE_TARGET_NODE_Y(10),
    SPACE_TARGET_NODE_X(30),
    TILE_CORRECTION_COORDINATE(1);

    int size;

    GridBoardSizeEnum(int size) {
      this.size = size;
    }

    public int get(){
      return this.size;
    }
  }

    public enum BulletCoordinatesEnum implements hasIntSize {
      SHOOT_START_POS_X(45),
      SHOOT_END_POS_X(45),
      SHOOT_START_POS_Y(50),
      SHOOT_END_POS_Y(50);

      int size;

      BulletCoordinatesEnum(int size) {
        this.size = size;
      }

    public int get(){
      return this.size;
    }
  }

  public enum GameSceneCoordinatesEnum implements hasIntSize {
    SIZE_BOARD_X(305),
    SIZE_BOARD_Y(630),
    SIZE_BOARD_X_MAIN(50),
    SIZE_BOARD_Y_MAIN(590),
    TIMER_DISPLAY_X(380),
    TIMER_DISPLAY_Y(10),
    SIZE_BOARD_TILE(9),
    POSITION_PLAYER_1_X(1),
    POSITION_PLAYER_1_Y(1),
    POSITION_PLAYER_2_X(5),
    POSITION_PLAYER_2_Y(5),
    LEVEL_SELECTED(3),
    PLAYER_NUMBER_1(1),
    PLAYER_NUMBER_2(2);

    int size;

    GameSceneCoordinatesEnum(int size) {
      this.size = size;
    }

    public int get(){
      return this.size;
    }
  }

  public static final String APP_NAME = "SILVER BULLET";
  public static final String TITLE_FONT = "Times New Roman";
  public static final String MENU_FONT = "Times New Roman";
  public static final int[] PLAYER_NUMBERS = new int[]{2, 3, 4};
  public static final int DEFAULT_PLAYER_NUMBER_INDEX = 0;
  @Deprecated
  public static final int[] BOARD_SIZES = new int[]{6, 9, 12};
  public static final int[] GAME_LEVEL_NUMBERS = new int[]{1, 2, 3};
  public static final int DEFAULT_GAME_LEVEL_INDEX = 0;

}
