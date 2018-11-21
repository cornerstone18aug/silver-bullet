package ca.ciccc.silverBullet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;

public class FileInputTest extends Application implements EventHandler<KeyEvent> {


    @Override
    public void handle(KeyEvent keyEvent) {

    }

        Group group = new Group();
        Circle circle1 = new Circle(600, 500, 150);
        Scene scene = new Scene(group, 1360, 768);
        Button cat = new Button();
        FileInput fileInput = new FileInput();
        Image img = fileInput.image("file:src/main/resources/cat.jpg");
        ImageView p1 = fileInput.imageView(img,600,500);
        AudioClip clip = fileInput.clip("file:src/main/resources/Disco.mp3");
        Image img1 = fileInput.image("https://dogecoin.com/imgs/doge.png");
        Point p = MouseInfo.getPointerInfo().getLocation();


        public static void main(String[] args) {
            launch();
        }

        @Override
        public void start(Stage stage) {
            AnimationTimer Timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    update();
                }
            };
            group.getChildren().add(circle1);


            stage.setScene(scene);
            stage.show();
            cat.setGraphic(p1);
            clip.play();
            scene.setOnKeyPressed(Event -> {
                if (Event.getCode() == KeyCode.W) {
                    circle1.setTranslateY(circle1.getTranslateY() - 20);
                    if(circle1.getTranslateY() < - 440)
                    {
                        circle1.setFill(new ImagePattern(img1));
                    }
                }
            });
        }

        public void update() {

        }

    }


