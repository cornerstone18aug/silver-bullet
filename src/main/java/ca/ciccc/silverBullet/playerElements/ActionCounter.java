package ca.ciccc.silverBullet.playerElements;

import ca.ciccc.silverBullet.gameBoard.GameScene;
import ca.ciccc.silverBullet.gameBoard.GridBoard;
import ca.ciccc.silverBullet.utils.ConstUtil;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ActionCounter extends Pane {

  private Circle[] actionNodes;
  private Text actionsText;
  private Circle[] manaNodes;
  private Text manaText;
  private int nodesEnabled = 0;
  private Circle playerImage;
  private Text playerText;
  int manaEnabled;

  public ActionCounter(Player parentPlayer) {
    manaEnabled = 3;

    actionNodes = new Circle[5];
    generateNodes();

    manaNodes = new Circle[3];
    generateMana();


    playerText = new Text("Player " + parentPlayer.getPlayerNumber());
    playerText.setFill(Color.WHITE);
      playerText.setFont(
              Font.font("Times New Roman", FontWeight.SEMI_BOLD, 12)
      );
      playerText.setTranslateX(300);
      playerText.setTranslateY(25);
      this.getChildren().add(playerText);


    actionsText = new Text("Actions");
    actionsText.setFill(Color.WHITE);
    actionsText.setFont(
        Font.font("Times New Roman", FontWeight.BOLD, 18)
    );
    this.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(4), Insets.EMPTY)));

    manaText = new Text("Mana");
      manaText.setFont(
              Font.font("Times New Roman", FontWeight.BOLD, 17)
      );
      manaText.setFill(Color.WHITE);
      manaText.setTranslateX(80);
      manaText.setTranslateY(35);
      this.getChildren().add(manaText);

    this.setPrefSize(380, 100 );

    actionsText.setTranslateX(70);
    actionsText.setTranslateY(65);
    this.getChildren().add(actionsText);

    Node playerToLookAt = parentPlayer.getPlayerNode();

    playerImage = new Circle(25, ((Circle) playerToLookAt).getFill());
    playerImage.setTranslateX(320);
    playerImage.setTranslateY(60);
    this.getChildren().add(playerImage);


  }

  private void generateNodes() {
    for (int i = 0; i < 5; i++) {
      actionNodes[i] = new Circle(10, Color.GRAY);
      actionNodes[i].setTranslateX((i * 25) + 150);
      actionNodes[i].setTranslateY(60);
      this.getChildren().add(actionNodes[i]);
    }
  }

  private void generateMana(){
      for (int i = 0; i < 3; i++) {
          manaNodes[i] = new Circle(10, Color.BLUE);
          manaNodes[i].setTranslateX((i * 25) + 150);
          manaNodes[i].setTranslateY(30);
          this.getChildren().add(manaNodes[i]);
      }
  }

  public void addAction() {
    nodesEnabled++;
    if (nodesEnabled > 5) {
      nodesEnabled = 5;
    }
    for (int i = 0; i < 5; i++) {
      if (i < nodesEnabled) {
        actionNodes[i].setFill(Color.RED);
      } else {
        actionNodes[i].setFill(Color.GRAY);
      }
    }
  }

  public void removeAction() {
    nodesEnabled--;
    if (nodesEnabled < 0) {
      nodesEnabled = 0;
    }
    for (int i = 0; i < 5; i++) {
      if (i >= 5 - nodesEnabled) {
        actionNodes[i].setFill(Color.RED);
      } else {
        actionNodes[i].setFill(Color.GRAY);
      }
    }
  }

  public void addShot(){
    manaEnabled++;
    if (nodesEnabled > 3) {
      nodesEnabled = 3;
    }
    for (int i = 0; i < 3; i++) {
      if (i < manaEnabled) {
        manaNodes[i].setFill(Color.BLUE);
      } else {
        manaNodes[i].setFill(Color.GRAY);
      }
    }
  }

  public void removeShot(){
    manaEnabled--;
    if (manaEnabled < 0) {
      manaEnabled = 0;
    }
    for (int i = 0; i < 3; i++) {
      if (i >= 3- manaEnabled) {
        manaNodes[i].setFill(Color.BLUE);
      } else {
        manaNodes[i].setFill(Color.GRAY);
      }
    }
  }

  public void clearActions() {
    for (int i = 0; i < 5; i++) {
      actionNodes[i].setFill(Color.GRAY);
    }
  }


  public void adjustManaNodes(int adjustment){
    for(Circle c : manaNodes){
      c.setTranslateX(c.getTranslateX() - adjustment);
    }
  }

  public void adjustActionNodes(int adjustments){
    for(Circle c : actionNodes){
      c.setTranslateX(c.getTranslateX() - adjustments);
    }
  }

  public Text getActionsText() {
    return actionsText;
  }

  public Text getManaText() {
    return manaText;
  }

  public Circle getPlayerImage() {
    return playerImage;
  }

  public Text getPlayerText() {
    return playerText;
  }

  public void adjustActionCounter(int numberOfPlayers, int i){
    if(numberOfPlayers > 2){
      this.setPrefSize(400 - (44 * numberOfPlayers), 100);

      this.getActionsText().setTranslateX(70 - (12 * numberOfPlayers));
      this.getManaText().setTranslateX(75 - (12 * numberOfPlayers));

      this.adjustActionNodes(12*numberOfPlayers);
      this.adjustManaNodes(12*numberOfPlayers);

      this.getPlayerImage().setTranslateX(this.getPlayerImage().getTranslateX() - (20 * numberOfPlayers));
      this.getPlayerText().setTranslateX(this.getPlayerText().getTranslateX() - (20 * numberOfPlayers));
      this.setTranslateX(ConstUtil.GameSceneCoordinatesEnum.SIZE_BOARD_X_MAIN.get() - (numberOfPlayers==4?48:0)  + (this.getPrefWidth() * i));

      this.getChildren().remove(playerImage);
      this.getChildren().remove(playerText);

    } else{
      this.setTranslateX(ConstUtil.GameSceneCoordinatesEnum.SIZE_BOARD_X_MAIN.get() +  20 + (this.getPrefWidth() * i));
    }
  }
}
