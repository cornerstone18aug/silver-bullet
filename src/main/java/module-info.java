module ca.ciccc.silverBullet {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    opens ca.ciccc.silverBullet.controller to javafx.fxml;
    opens ca.ciccc.silverBullet to javafx.graphics, javafx.fxml;
}
