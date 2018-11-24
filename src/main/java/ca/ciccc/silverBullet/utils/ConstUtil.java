package ca.ciccc.silverBullet.utils;

public class ConstUtil {

  private ConstUtil() {
  }

  enum DisplaySizeEnum {
    EXTERNAL_FRAME_WIDTH(1050),
    EXTERNAL_FRAME_HEIGHT(600);

    int size;
    DisplaySizeEnum(int size) {
      this.size = size;
    }
  }

}
