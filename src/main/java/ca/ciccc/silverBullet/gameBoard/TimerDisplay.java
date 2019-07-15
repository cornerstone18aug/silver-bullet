package ca.ciccc.silverBullet.gameBoard;

import ca.ciccc.silverBullet.playerElements.Player;
import ca.ciccc.silverBullet.utils.MediaUtil;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class TimerDisplay extends HBox {

  private Text timerText;
  private Circle[] playerImages;

  TimerDisplay(List<Player> players) {

    playerImages = new Circle[players.size()];
    for (int i = 0; i < players.size(); i++) {
      playerImages[i] = ((Circle) players.get(i).getPlayerNode());
    }

//        this.setBackground(new Background(new BackgroundFill(Color.SADDLEBROWN, new CornerRadii(4), Insets.EMPTY)));
    Image image = MediaUtil.createImage("/images/Woodboard.png");
    this.setBackground(new Background(
        new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

    this.setPadding(new Insets(10));
    double currentTime = 0;
    timerText = new Text();
    timerText.setText(doubleToTime(currentTime));
    timerText.setFill(Color.WHITE);
    timerText.setFont(
        Font.font("Times New Roman", FontWeight.BOLD, 20)
    );
    for (int i = 0; i < playerImages.length; i++) {
      if (i == playerImages.length / 2) {
        getChildren().add(timerText);
      }
      Circle imageToAdd = new Circle(20, ((Circle) playerImages[i]).getFill());
      playerImages[i] = imageToAdd;
      getChildren().add(imageToAdd);

    }
  }

  private String doubleToTime(double d) {
    int minutes = (int) d / (60);
    int seconds = (int) d % 60;
    return String.format("%d:%02d", minutes, seconds);

  }

  void timerUpdate(double d) {
    timerText.setText(doubleToTime(d));
  }

  void removePlayerImage(Player playerToRemove) {
    ColorAdjust darken = new ColorAdjust();
    darken.setBrightness(-.85);
    playerImages[playerToRemove.getPlayerNumber() - 1].setEffect(darken);
    playerImages[playerToRemove.getPlayerNumber() - 1] = null;
  }

  void setHighlight(int playerNumber) {
    Circle imageToCheck = playerImages[playerNumber];
    ColorAdjust darken = new ColorAdjust();
    darken.setBrightness(-.4);
    ColorAdjust lighten = new ColorAdjust();
    lighten.setBrightness(0);
    imageToCheck.setEffect(lighten);

    for (Circle playerImage : playerImages) {
      if (playerImage != null) {
        if (!playerImage.equals(imageToCheck)) {
          playerImage.setEffect(darken);
        }
      }
    }
  }

  void highlightAll() {
    ColorAdjust lighten = new ColorAdjust();
    lighten.setBrightness(0);

    for (Circle playerImage : playerImages) {
      if (playerImage != null) {
        playerImage.setEffect(lighten);
      }
    }
  }
}
