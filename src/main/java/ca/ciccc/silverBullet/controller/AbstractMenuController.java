package ca.ciccc.silverBullet.controller;


import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.media.AudioClip;

public abstract class AbstractMenuController extends AbstractController {

  protected static final AudioClip MENU_CLIP =
      MediaUtil.createClip("file:src/main/resources/Test/MenuMii.wav");
}
