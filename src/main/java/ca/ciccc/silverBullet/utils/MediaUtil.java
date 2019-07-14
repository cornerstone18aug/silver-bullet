package ca.ciccc.silverBullet.utils;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class MediaUtil {
  private static final String PATH_TO_RESOURCES = "jrt:/SilverBulletModule/ca/ciccc/silverBullet";

  private MediaUtil() {
  }

  public static Image createImage(String filePath) {
    Image result = new Image(PATH_TO_RESOURCES + filePath);
    if (result.getException() != null)
      result.getException().printStackTrace();

    return result;
  }

  public static AudioClip createClip(String filePath) throws RuntimeException {
    return new AudioClip(PATH_TO_RESOURCES + filePath);
  }
}
