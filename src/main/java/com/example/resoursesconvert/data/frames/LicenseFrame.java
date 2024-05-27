package com.example.resoursesconvert.data.frames;

import com.example.resoursesconvert.Frame;
import com.example.resoursesconvert.ResourceConverter;
import com.example.resoursesconvert.data.Resources;
import com.example.resoursesconvert.data.license.License;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LicenseFrame implements Frame {

    private final Stage stage;
    private Scene scene;

    @FXML
    private TextField licenseField;
    @FXML
    private Button licenseButton;

    public LicenseFrame(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            try {
                Resources resources = new Resources();
                FXMLLoader loader = new FXMLLoader(resources.getFXML("license-view"));
                loader.setController(this);
                Parent root = loader.load();
                Scene scene = new Scene(root, 1050, 675);
                this.scene = scene;
                stage.setTitle(" ");
                stage.setResizable(false);
                licenseButton.setOnAction(actionEvent -> {
                    System.out.println("123");
                    String licenseText = licenseField.getText();
                    License license = new License(
                            "6QgyURDL4s6X00KxfNatwK7PkWbHSp488kqPXugfTekpqNqfgPK4M8LU18A0dM2G3oyuUhxxRHoN1u3hz9fIAr46e06QgyURDL4s6X00KxfNatwK7PkWbHSp488kqPXugfTekpqNqfgPK4M8LU18A0dM2G3oyuUhxxRHoN1u3hz9fIAr46e0",
                            licenseText,
                            "http://85.159.231.2",
                            "ResourceConverter");
                    license.request();
                    if (license.isValid()) {
                        Frame frame = new MainFrame(stage);
                        frame.setVisible(true);
                    }
                });
                Frame.switchToScene(stage.getScene(), scene, stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            stage.hide();
        }
    }

    @Override
    public void onInitialize() {

    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

}
