package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.SilverBulletApp;
import ca.ciccc.silverBullet.utils.MediaUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * HowToPlayController
 *
 * @author Masa
 */
public class HowToPlayController extends AbstractMenuController {

  private static HowToPlayController instance;
  private static Scene SCENE;
  private static final String[] TITLE_ARY = {
      "COMMAND PHASE",
      "EXECUTION PHASE"
  };
  private static final String[] DESCRIPTION_ARY = {
      "Input 5 commands one after another.",
      "Those are executed one by one. If being shot, the player dies"
  };
  private static final String[] IMAGE_PATH_ARY = {
      "file:src/main/resources/images/Character/Fire/Fire.png",
      "file:src/main/resources/images/Character/Wind/Wind.png"
  };

  private static final Color SLIDER_NOT_SELECTED_COLOR =
      Color.rgb(31, 147, 255, 0.4);
  private static final Color SLIDER_SELECTED_COLOR =
      Color.rgb(31, 147, 255, 1);

  private List<Image> imageList = new ArrayList<>();
  private List<Circle> imageSliderList = new ArrayList<>();

  @FXML
  private Text titleText;
  @FXML
  private Text descriptionText;
  @FXML
  private ImageView imageView;
  @FXML
  private HBox imageSlider;
  @FXML
  private Polygon leftSign;
  @FXML
  private Polygon rightSign;

  static {
    FXMLLoader fxmlLoader = new FXMLLoader();
    try {
      fxmlLoader.load(
          HowToPlayController.class.getModule().getResourceAsStream(
              "fxml/howToPlay.fxml"
          ));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Parent parent = fxmlLoader.getRoot();
    Scene scene = new Scene(parent);
    scene.setFill(Color.TRANSPARENT);
    SCENE = scene;
    instance = fxmlLoader.getController();

    // Set images and texts
    instance.imageList.addAll(Arrays.stream(IMAGE_PATH_ARY)
        .map(MediaUtil::createImage)
        .collect(Collectors.toList()));
    instance.imageView.setImage(instance.imageList.get(0));
    instance.titleText.setText(TITLE_ARY[0]);
    instance.descriptionText.setText(DESCRIPTION_ARY[0]);

    // Add image slider
    IntStream.range(0, IMAGE_PATH_ARY.length).forEach(i -> {
      Circle imageSlider = instance.createImageSlider();
      instance.imageSliderList.add(imageSlider);
      instance.imageSlider.getChildren().add(
          instance.imageSlider.getChildren().size() - 1,
          imageSlider
      );
      if (i != IMAGE_PATH_ARY.length - 1) {
        HBox.setMargin(imageSlider, new Insets(0, 10, 0, 0));
      }
    });

    instance.imageSliderList.get(0).setFill(SLIDER_SELECTED_COLOR);

  }

  /**
   * Return singleton instance
   *
   * @return instance
   */
  public static HowToPlayController getInstance() {
    synchronized (HowToPlayController.class) {
      if (instance == null) {
        instance = new HowToPlayController();
      }
      return instance;
    }
  }

  public void show() {
    SilverBulletApp.primaryStage.setScene(SCENE);
  }

  @FXML
  void OnEntered(MouseEvent event) {

  }

  @FXML
  void OnExited(MouseEvent event) {

  }

  @FXML
  void OnPressed(MouseEvent event) {

  }

  @FXML
  void onLeftSignClicked(MouseEvent event) {
    int index = this.imageList.indexOf(this.imageView.getImage());
    this.imageSliderList.get(index).setFill(SLIDER_NOT_SELECTED_COLOR);
    if (index == 0) {
      index = this.imageList.size();
    }

    this.slide(--index);
  }

  @FXML
  void onRightSignClicked(MouseEvent event) {
    int index = this.imageList.indexOf(this.imageView.getImage());
    this.imageSliderList.get(index).setFill(SLIDER_NOT_SELECTED_COLOR);
    if (index == this.imageList.size() - 1) {
      index = -1;
    }

    this.slide(++index);
  }

  private void slide(int newIndex) {
    this.titleText.setText(TITLE_ARY[newIndex]);
    this.descriptionText.setText(DESCRIPTION_ARY[newIndex]);
    this.imageView.setImage(instance.imageList.get(newIndex));
    this.imageSliderList.get(newIndex).setFill(SLIDER_SELECTED_COLOR);
  }

  @FXML
  public void onBackToMenuClicked() {
    MenuController.getInstance().show();
  }

  private Circle createImageSlider() {
    Circle result = new Circle();
    result.setRadius(15);
    result.setFill(SLIDER_NOT_SELECTED_COLOR);
    return result;
  }

}
