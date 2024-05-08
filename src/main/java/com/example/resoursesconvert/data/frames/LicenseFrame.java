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

    @FXML
    public Button gpButton;
    @FXML
    public Button liteMaticaButton;

    public private final Stage stage;
    private Scene scene;

    @FXML
    public Button gpButton;
    @FXML
    public Button liteMaticaButton;

    private static final String IMAGE_DIRECTORY;

    static {
        try {
            IMAGE_DIRECTORY = HelloApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\textures";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int IMAGE_SIZE = 100;
    private static final int SPACING = 10;
    public static HashMap<Integer, String> images = new HashMap<Integer, String>();

    public MainFrame(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            try {
                Resources resources = new Resources();
                FXMLLoader loader = new FXMLLoader(resources.getFXML("hello-view"));
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

    @FXML
    private void initialize() {
        AtomicReference<HashMap<String, Integer>> map = new AtomicReference<>(new HashMap<>());

        liteMaticaButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите текстовый файл");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                System.out.println("Выбран файл: " + selectedFile.getAbsolutePath());
                Frame frame = new LiteMaticaConverter(selectedFile.getAbsolutePath(), stage);
                frame.setVisible(true);
                map.get().keySet().forEach(key -> {
                    System.out.println(key + " : " + map.get().get(key));
                });
            }
        });

        gpButton.setOnAction(e -> {
            Frame frame = new ChangeCraftFrame(stage, images);
            frame.setVisible(true);
        });
    }(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            try {
                Resources resources = new Resources();
                FXMLLoader loader = new FXMLLoader(resources.getFXML("hello-view"));
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

    @FXML
    private void initialize() {



        gpButton.setOnAction(e -> {
            Frame frame = new ChangeCraftFrame(stage, images);
            frame.setVisible(true);
        });

    }

}
