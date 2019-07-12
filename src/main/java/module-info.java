module SilverBulletModule {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires java.desktop;

    opens ca.ciccc.silverBullet.controller to javafx.fxml;
    opens ca.ciccc.silverBullet to javafx.graphics, javafx.fxml, java.desktop;

    exports ca.ciccc.silverBullet;
}
