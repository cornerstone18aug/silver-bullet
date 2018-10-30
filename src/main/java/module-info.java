module com.altran.igjava.demo.fx.application {

    requires javafx.graphics;
    requires javafx.controls;

    opens com.altran.igjava.demoserver to javafx.graphics;
}