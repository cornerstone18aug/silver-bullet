package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.ModalUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

/**
 * HowToPlayController
 *
 * @author Masa
 */
Hao-Tse/dev
public class SettingsController extends AbstractController {

  private static SettingsController instance;
  private static Scene SCENE;

  @FXML
  private ComboBox<Integer> howManyPlayersCombo;
  @FXML
  private ComboBox<Integer> boardSizeCombo;
  @FXML
  private ComboBox<Integer> levelCombo;

  static {
    FXMLLoader fxmlLoader = new FXMLLoader();
    try {
      fxmlLoader.load(
          SettingsController.class.getModule().getResourceAsStream("fxml/settings.fxml")
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
    Parent parent = fxmlLoader.getRoot();
    Scene scene = new Scene(parent);
    scene.setFill(Color.TRANSPARENT);
    SCENE = scene;
    instance = fxmlLoader.getController();
  }

  /**
   * Return singleton instance
   *
   * @return instance
   */
  public static SettingsController getInstance() {
    synchronized (SettingsController.class) {
      if (instance == null) {
        instance = new SettingsController();
      }
      return instance;
    }
  }

  public void show() {
    if (howManyPlayersCombo.getItems().size() == 0) {
      // Set combo values
      this.howManyPlayersCombo.getItems()
          .addAll(
              Arrays.stream(ConstUtil.PLAYER_NUMBERS)
                  .boxed()
                  .collect(Collectors.toList())
          );
      this.boardSizeCombo.getItems()
          .addAll(
              Arrays.stream(ConstUtil.BOARD_SIZES)
                  .boxed()
                  .collect(Collectors.toList())
          );
      this.levelCombo.getItems()
          .addAll(
              Arrays.stream(ConstUtil.LEVEL_NUMBERS)
                  .boxed()
                  .collect(Collectors.toList())
          );

    }
    SilverBulletApp.primaryStage.setScene(SCENE);
  }

  private boolean validate() {
    return !this.howManyPlayersCombo.getSelectionModel().isEmpty()
        && !this.boardSizeCombo.getSelectionModel().isEmpty()
        && !this.levelCombo.getSelectionModel().isEmpty();
  }

  @FXML
  public void onStartClicked() {

    if (!validate()) {
      ModalUtil.alert("INVALID SETTINGS",
          "There is an empty option"
      );
    } else {
      ModalUtil.confirm("START",
          "Are you ready?",
          () -> GameController.getInstance().show(
              this.howManyPlayersCombo.getSelectionModel().getSelectedItem(),
              this.boardSizeCombo.getSelectionModel().getSelectedItem(),
              this.levelCombo.getSelectionModel().getSelectedItem()
          )
      );
    }
  }

  @FXML
  public void onBackToMenuClicked() {
    ModalUtil.confirm("BACK TO MENU",
        "Go back to menu?",
        () -> MenuController.getInstance().show()
    );
  }

  @FXML
  public void OnEntered() {
    System.out.println("Entered");
  }

  @FXML
  public void OnPressed() {
    System.out.println("Pressed");
  }

  @FXML
  public void OnExited() {
    System.out.println("Exited");
  }

}
