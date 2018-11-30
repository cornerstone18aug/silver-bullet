package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TimerDisplay extends HBox {
    double currentTime;
    Text timerText;
    Circle[] playerImages;
    private Image image = MediaUtil.createImage("file:src/main/resources/images/Woodboard.png");


    public TimerDisplay(List<Player> players) {

        playerImages = new Circle[players.size()];
        for (int i = 0; i < players.size(); i++){
            playerImages[i] = ((Circle) players.get(i).getPlayerNode());
        }

//        this.setBackground(new Background(new BackgroundFill(Color.SADDLEBROWN, new CornerRadii(4), Insets.EMPTY)));
        this.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        this.setPadding(new Insets(10));
        currentTime = 0;
        timerText = new Text();
        timerText.setText(doubleToTime(currentTime));
        timerText.setFill(Color.WHITE);
        timerText.setFont(
                Font.font("Times New Roman", FontWeight.BOLD, 20)
        );
        for(int i = 0; i<playerImages.length; i++){
            if(i == playerImages.length / 2){
                getChildren().add(timerText);
            }
            Circle imageToAdd = new Circle(20,((Circle)playerImages[i]).getFill());
            playerImages[i] = imageToAdd;
            getChildren().add(imageToAdd);

        }
    }

    private String doubleToTime(double d){

        int minutes =(int) d / (60);
        int seconds = (int)d % 60;
        String str = String.format("%d:%02d", minutes, seconds);
        return str;

    }

    public void timerUpdate(double d){
        timerText.setText(doubleToTime(d));
    }

    public void removePlayerImage(Player playerToRemove){
        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-.85);
        playerImages[playerToRemove.getPlayerNumber()-1].setEffect(darken);
        playerImages[playerToRemove.getPlayerNumber()-1] = null;
    }

    public void setHighlight(int playerNumber){
        Circle imageToCheck = playerImages[playerNumber];
        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-.4);
        ColorAdjust lighten = new ColorAdjust();
        lighten.setBrightness(0);
        imageToCheck.setEffect(lighten);

        for(int i = 0; i < playerImages.length; i ++){
            if(playerImages[i]!= null){
                if(!playerImages[i].equals(imageToCheck)){
                    playerImages[i].setEffect(darken);
                }
            }
        }
    }

    public void highlightAll(){
        ColorAdjust lighten = new ColorAdjust();
        lighten.setBrightness(0);

        for(int i = 0; i < playerImages.length; i ++){
            if(playerImages[i]!= null){
                playerImages[i].setEffect(lighten);
            }
        }
    }
}
