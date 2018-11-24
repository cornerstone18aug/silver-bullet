package ca.ciccc.silverBullet.playerElements;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ActionCounter extends Pane {
    Circle[] actionNodes;
    Text playerNumberText;
    int nodesEnabled = 0;

    public ActionCounter(int playerNumber) {
        actionNodes = new Circle[5];
        generateNodes();
        playerNumberText = new Text("Player " + playerNumber);
        playerNumberText.setFill(Color.WHITE);
        playerNumberText.setFont(
                Font.font("Times New Roman", FontWeight.BOLD, 20)
        );
        playerNumberText.setTranslateX(15);
        playerNumberText.setTranslateY(30);
        this.getChildren().add(playerNumberText);

    }

    private void generateNodes(){
        for(int i = 0; i<5; i++){
            actionNodes[i] = new Circle(10, Color.GRAY);
            actionNodes[i].setTranslateX(i*25);
            this.getChildren().add(actionNodes[i]);
        }
    }

    public void addAction(){
        nodesEnabled++;
        if(nodesEnabled>5){
            nodesEnabled = 5;
        }
        for(int i = 0; i<5; i++){
            if(i<nodesEnabled){
                actionNodes[i].setFill(Color.RED);
            } else{
                actionNodes[i].setFill(Color.GRAY);
            }
        }
    }

    public void removeAction(){
        nodesEnabled--;
        if(nodesEnabled<0){
            nodesEnabled = 0;
        }
        for(int i = 0; i<5; i++){
            if(i>=5-nodesEnabled){
                actionNodes[i].setFill(Color.RED);
            } else{
                actionNodes[i].setFill(Color.GRAY);
            }
        }
    }

    public void clearActions(){
        for(int i = 0; i<5; i++){
            actionNodes[i].setFill(Color.GRAY);
        }
    }
}
