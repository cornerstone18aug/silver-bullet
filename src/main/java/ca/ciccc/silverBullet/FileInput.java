package ca.ciccc.silverBullet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class FileInput {


    public ImageView imageView(Image image, double height, double width) {
        ImageView p1 = null;
        p1 = new ImageView(image);
        p1.setFitHeight(height);
        p1.setFitWidth(width);
        p1.setPreserveRatio(true);
        return p1;
    }

    public Image image(String filePath) {
        Image img = new Image(filePath);
        return img;
    }

    public AudioClip clip(String filePath) {
        AudioClip clip = new AudioClip(filePath);
        return clip;
    }

}
