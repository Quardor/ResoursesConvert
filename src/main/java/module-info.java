module com.example.resoursesconvert {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires org.yaml.snakeyaml;
    requires static lombok;

    opens com.example.resoursesconvert to javafx.fxml;
    exports com.example.resoursesconvert;

    opens com.example.resoursesconvert.data.frames to javafx.fxml;
}