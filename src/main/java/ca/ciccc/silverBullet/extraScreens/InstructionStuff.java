package ca.ciccc.silverBullet.extraScreens;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class InstructionStuff extends Pane {
    public InstructionStuff() {
        Rectangle leftImage = new Rectangle(180, 160);
        leftImage.setFill(new ImagePattern(MediaUtil.createImage(
            "/images/howtoplayL.png")));

        leftImage.setTranslateX(5);
        leftImage.setTranslateY(425);

        this.getChildren().add(leftImage);

        Rectangle rightImage = new Rectangle(180, 160);
        rightImage.setFill(new ImagePattern(MediaUtil.createImage(
            "/images/howtoplayR.png")));
        rightImage.setTranslateX(720);
        rightImage.setTranslateY(425);
        this.getChildren().add(rightImage);
    }
}
