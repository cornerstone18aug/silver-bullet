package ca.ciccc.silverBullet.utils;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class MediaUtil {
  private static final String PATH_TO_RESOURCES = "jrt:/SilverBulletModule/ca/ciccc/silverBullet";

  private MediaUtil() {
  }

  public static Image createImage(String filePath) {
    return new Image(PATH_TO_RESOURCES + filePath);
  }

  public static AudioClip createClip(String filePath) throws RuntimeException {
    return new AudioClip(PATH_TO_RESOURCES + filePath);
  }
}
