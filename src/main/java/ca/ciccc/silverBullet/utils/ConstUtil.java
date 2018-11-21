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

  private ConstUtil() {}

  public static String getRbString(String key) {
    return RESOURCE_BUNDLE.getString(key);
  }

  public interface hasDoubleSize {
    double get();
  }

  public enum DisplaySizeEnum implements hasDoubleSize {
    EXTERNAL_FRAME_W(900),
    EXTERNAL_FRAME_H(600),
    TITLE_W(EXTERNAL_FRAME_W.get()),
    TITLE_H(80),
    MENU_ITEM_W(300),
    MENU_ITEM_H(80),
    MENU_IMAGE_W(180),
    MENU_IMAGE_H(450);

    double size;
    DisplaySizeEnum(double size) {
      this.size = size;
    }

    public double get(){
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

    public double get(){
      return this.size;
    }
  }

  public static final String APP_NAME = "SILVER BULLET";
  public static final String TITLE_FONT = "Times New Roman";
  public static final String MENU_FONT = "Times New Roman";

}
