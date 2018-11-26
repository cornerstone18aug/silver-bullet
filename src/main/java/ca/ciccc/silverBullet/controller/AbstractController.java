package ca.ciccc.silverBullet.controller;

import ca.ciccc.silverBullet.utils.MediaUtil;
import javafx.scene.media.AudioClip;

public abstract class AbstractController {
    MediaUtil mediaUtil = new MediaUtil();
    AudioClip menuClip = mediaUtil.clip("file:src/main/resources/Test/Menu.wav");
}
