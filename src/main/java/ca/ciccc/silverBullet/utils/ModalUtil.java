package ca.ciccc.silverBullet.utils;

import ca.ciccc.silverBullet.controller.MenuController;
import ca.ciccc.silverBullet.utils.ConstUtil.DisplaySizeEnum;
import java.util.function.Supplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Display modal
 *
 * @author Masa
 */
public class ModalUtil {

  private ModalUtil() {
  }

  public static Stage confirm(String title, String message, Runnable action) {
    Stage modalStage = ModalUtil.createModalStage(title);
    Pane parentPane = ModalUtil.createModalPane();

    Label label = new Label();
    label.setText(message);

    Button executeButton = new Button(ConstUtil.getRbString("modal.ok"));
    executeButton.setOnAction(e -> {
      action.run();
      modalStage.close();
    });

    Button closeButton = new Button(ConstUtil.getRbString("modal.cancel"));
    closeButton.setOnAction(e -> modalStage.close());

    VBox textBox = new VBox(10);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().add(label);
    textBox.setTranslateX(15);
    textBox.setTranslateY(20);

    HBox btnBox = new HBox(10);
    btnBox.setAlignment(Pos.CENTER);
    btnBox.setTranslateX(50);
    btnBox.setTranslateY(50);
    btnBox.getChildren().addAll(executeButton, closeButton);

    parentPane.getChildren().addAll(textBox, btnBox);

    modalStage.setScene(new Scene(parentPane));
    /* Its need to be close before do other things out of the window */
    modalStage.showAndWait();

    return modalStage;
  }

  public static void alert(String title, String message) {
    Stage modalStage = ModalUtil.createModalStage(title);
    Pane parentPane = ModalUtil.createModalPane();

    Label label = new Label();
    label.setText(message);

    Button okButton = new Button(ConstUtil.getRbString("modal.ok"));
    okButton.setOnAction(e -> modalStage.close());

    VBox textBox = new VBox(10);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().add(label);
    textBox.setTranslateX(15);
    textBox.setTranslateY(20);

    HBox btnBox = new HBox(10);
    btnBox.setAlignment(Pos.CENTER);
    btnBox.setTranslateX(50);
    btnBox.setTranslateY(50);
    btnBox.getChildren().addAll(okButton);

    parentPane.getChildren().addAll(textBox, btnBox);

    modalStage.setScene(new Scene(parentPane));
    /* Its need to be close before do other things out of the window */
    modalStage.showAndWait();

  }

  private static Stage createModalStage(String title) {
    Stage modalStage = new Stage();

    /* We don't permit to press other this out of this window if we don't close first */
    modalStage.initModality(Modality.APPLICATION_MODAL);
    modalStage.setTitle(title);

    return modalStage;
  }

  private static Pane createModalPane() {
    Pane parentPane = new Pane();
    parentPane.setPrefSize(DisplaySizeEnum.MODAL_MIN_W.get(),
        DisplaySizeEnum.MODAL_MIN_H.get());
    return parentPane;
  }

}
