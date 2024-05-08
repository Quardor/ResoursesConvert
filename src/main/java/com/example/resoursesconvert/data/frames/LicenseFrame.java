package com.example.resoursesconvert.data.frames;

import com.example.resoursesconvert.Frame;
import com.example.resoursesconvert.HelloApplication;
import com.example.resoursesconvert.LiteMaticaConverter;
import com.example.resoursesconvert.data.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class LicenseFrame implements Frame {

    private final Stage stage;
    private Scene scene;

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
