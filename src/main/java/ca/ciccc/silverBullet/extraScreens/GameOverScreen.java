package ca.ciccc.silverBullet.extraScreens;

import ca.ciccc.silverBullet.controller.AbstractMenuController;
import ca.ciccc.silverBullet.controller.MenuController;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class GameOverScreen extends Pane {
    Rectangle backgroundImage;
    Text gameOverText;
    Button testBackToMenu;

    public GameOverScreen(int winningPlayerNumber){

        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");
        gameOverText = new Text(String.format("Player %d wins!", winningPlayerNumber));
        testBackToMenu = new Button("Back To Menu");
        gameOverText.setFont(
                Font.font("Times New Roman", FontWeight.BOLD, 100)
        );

        gameOverText.setFill(Color.WHITE);

        testBackToMenu.setFont(
                Font.font("Times New Roman", FontWeight.BOLD, 30)
        );

        this.setPrefSize(1000, 1000);


        gameOverText.setTranslateX(150);
        gameOverText.setTranslateY(300);

        this.getChildren().add(gameOverText);

        testBackToMenu.setTranslateX(700 / 2);
        testBackToMenu.setTranslateY(350);

        this.getChildren().add(testBackToMenu);

        testBackToMenu.setOnMouseClicked(mouseEvent -> {
            MenuController.getInstance().show();});
    }
}
