package ca.ciccc.silverBullet.utils;

public class ConstUtil {

  private ConstUtil() {
  }

  public enum DisplaySizeEnum {
    EXTERNAL_FRAME_WIDTH(1050),
    EXTERNAL_FRAME_HEIGHT(600);

    double size;
    DisplaySizeEnum(double size) {
      this.size = size;
    }
    public double getSize(){
      return this.size;
    };
  }

  public static final String APP_NAME = "SILVER BULLET";

}
