package ca.ciccc.silverBullet.controller;

import FileInput.FileInput;
import javafx.scene.media.AudioClip;

public abstract class AbstractController {
    FileInput fileInput = new FileInput();
    AudioClip menuClip = fileInput.clip("file:src/main/resources/Test/Menu.wav");
}
