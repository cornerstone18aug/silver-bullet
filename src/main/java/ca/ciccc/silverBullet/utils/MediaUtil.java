package ca.ciccc.silverBullet.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class MediaUtil {

  private MediaUtil() {
  }

  public static ImageView createImageView(Image image, double height, double width) {
    ImageView p1 = new ImageView(image);
    p1.setFitHeight(height);
    p1.setFitWidth(width);
    p1.setPreserveRatio(true);
    return p1;
  }

  public static Image createImage(String filePath) {
    Image img = new Image(filePath);
    return img;
  }

  public static AudioClip createClip(String filePath) {
    AudioClip clip = new AudioClip(filePath);
    return clip;
  }

}
