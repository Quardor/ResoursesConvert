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

        licenseButton.setOnAction(actionEvent -> {
            if (!licenseField.getText().isEmpty()) {
                String licenseText = licenseField.getText();
                License license = new License("", licenseText, "", ResourceConverter.class.getName());
                license.request();
                if (license.isValid()) {
                    Frame frame = new MainFrame(stage);
                    frame.setVisible(true);
                }
            }
        });

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
