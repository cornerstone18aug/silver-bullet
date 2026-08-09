package ca.ciccc.silverBullet.utils;

import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class MediaUtil {

  private static final String RESOURCE_ROOT = "/ca/ciccc/silverBullet";

  private MediaUtil() {
  }

  public static Image createImage(String filePath) {
    Image result = new Image(resolve(filePath));
    if (result.getException() != null) {
      result.getException().printStackTrace();
    }
    return result;
  }

  public static AudioClip createClip(String filePath) throws RuntimeException {
    return new AudioClip(resolve(filePath));
  }

  /**
   * Resolve a resource to a loadable URL via the class loader. This yields a
   * {@code jrt:} URL inside the jlink runtime image and a {@code file:}/
   * {@code jar:} URL when running from the classpath (dev and tests), so assets
   * load in every environment. Falls back to the raw path when the resource is
   * missing, keeping asset loading non-fatal.
   */
  private static String resolve(String filePath) {
    URL url = MediaUtil.class.getResource(RESOURCE_ROOT + filePath);
    return url != null ? url.toExternalForm() : RESOURCE_ROOT + filePath;
  }
}
