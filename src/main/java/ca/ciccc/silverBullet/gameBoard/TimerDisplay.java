package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.playerElements.Player;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TimerDisplay extends HBox {
    double currentTime;
    Text timerText;
    List<Node> playerImages;

    public TimerDisplay(List<Player> players) {

        playerImages = new ArrayList<>();
        for (Player p : players){
            playerImages.add(p.getPlayerNode());
        }
        currentTime = 0;
        timerText = new Text();
        timerText.setText(doubleToTime(currentTime));
        timerText.setFill(Color.WHITE);
        timerText.setFont(
                Font.font("Times New Roman", FontWeight.BOLD, 20)
        );
        for(int i = 0; i<playerImages.size(); i++){
            if(i == 1){
                getChildren().add(timerText);
                getChildren().add(playerImages.get(i));
            } else{
                getChildren().add(playerImages.get(i));
            }
        }
    }

    private String doubleToTime(double d){
        int minutes;
        int seconds;
        if(d>60){
            minutes = (int) d / 60;
            seconds = (int) (d - (60 * minutes));
        } else {
            minutes = 0;
            seconds = (int) d;
        }
        return String.format("%2d:%2d", minutes, seconds);

    }

    public void timerUpdate(double d){
        timerText.setText(doubleToTime(d));

    }
}
