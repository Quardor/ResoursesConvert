package com.example.resoursesconvert.data.frames;

import com.example.resoursesconvert.Frame;
import com.example.resoursesconvert.HelloApplication;
import com.example.resoursesconvert.data.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftFrame implements Frame{

    @FXML
    public StackPane stackPane;
    @FXML
    private Button backButton;

    public static CraftFrame frame = null;

    private final Stage stage;
    private int pageNumber = 0;
    private String craftPath;

    @Getter
    private HashMap<String, Integer> resources;

    private int count;

    private static HashMap<Integer, String> images;

    public CraftFrame(Stage stage, String craftPath, HashMap<Integer, String> image, HashMap<String, Integer> resources, int count) {
        this.stage = stage;
        this.resources = new HashMap<>();
        this.craftPath = craftPath;
        images = image;
        this.count = count;
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            try {
                Resources resources = new Resources();
                FXMLLoader fxmlLoader = new FXMLLoader(resources.getFXML("change-craft"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, 1050, 675);
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
    public void initialize() {

        resources.clear();

        backButton = new Button(" ");
        backButton.getStyleClass().add("backButton");

        Yaml yaml = new Yaml();

        try {
            String filePath = CraftFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\data\\crafts\\crafts.yml";
            FileInputStream fis = new FileInputStream(filePath);
            Map<String, Map<String, Map<String, Integer>>> data = yaml.load(fis);

            Map<String, Integer> craft = data.get("crafts").get(craftPath);

            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(10);
            flowPane.setVgap(10);
            for (Map.Entry<String, Integer> materialEntry : craft.entrySet()) {
                String materialName = materialEntry.getKey();
                int quantity = materialEntry.getValue();
                resources.put(materialName, quantity*count);
            }
            resources.forEach((k, v) -> {
                loadButton(k, flowPane, v, "items.yml");
            });
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setMaxHeight(541);
            scrollPane.setMaxWidth(1080);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background: rgba(0,0,0,0);");
            scrollPane.setContent(flowPane);
            stackPane.getChildren().add(scrollPane);

        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        stackPane.getChildren().add(backButton);
        backButton.setOnAction(e -> {
            Frame frame = new ChangeCraftFrame(stage, images);
            frame.setVisible(true);
            CraftFrame.frame = null;
        });
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public Scene getScene() {
        return null;
    }

    public static void loadButton(String itemPath, FlowPane flowPane, int count, String fileName){

        if(frame == null) return;

        Yaml yaml = new Yaml();

        HashMap<String, Integer> res = frame.getResources();

        try {
            String newfilePath = HelloApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\data\\items\\" + fileName;
            FileInputStream fis = new FileInputStream(newfilePath);
            Map<String, Map<String, Map<String, String>>> data = yaml.load(fis);
            Map<String, Map<String, String>> items = data.get("items");

            // Поиск нужного предмета
            Map<String, String> targetItemDetails = items.get(itemPath);
            if (targetItemDetails != null) {
                Button button = new Button();
                Image image = new Image(images.get(targetItemDetails.get("icon")));
                ImageView imageView = new ImageView(image);
                System.out.println(itemPath);

                button.setGraphic(imageView);
                button.setPrefSize(50, 50);

                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);

                button.setGraphic(imageView);
                Text textField = new Text();

                if(count/64 == 0) {
                    textField = new Text(String.valueOf(count));
                } else {
                    textField = new Text(String.valueOf(count/64) + "Ст." + String.valueOf(count%64));
                }
                textField.setFont(Font.font("Arial", FontWeight.NORMAL, 24));

                textField.setStyle("-fx-fill: white;");

                if(targetItemDetails.get("craft") != null){
                    button.setOnAction(e -> {
                        flowPane.getChildren().clear();
                        res.remove(itemPath);
                        String filePath = null;
                        FileInputStream fist = null;
                        try {
                            filePath = CraftFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\data\\crafts\\crafts.yml";
                            fist = new FileInputStream(filePath);
                        } catch (FileNotFoundException | URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        }
                        Map<String, Map<String, Map<String, Integer>>> data1 = yaml.load(fist);

                        Map<String, Integer> craft = data1.get("crafts").get(targetItemDetails.get("craft"));


                        for (Map.Entry<String, Integer> materialEntry : craft.entrySet()) {
                            String materialName = materialEntry.getKey();
                            int quantity = materialEntry.getValue();
                            if(!res.containsKey(materialName)){
                                res.put(materialName, quantity*count);
                            }else{
                                res.put(materialName, res.get(materialName) + quantity*count);
                            }
                        }
                        res.forEach((k, v) -> {
                            loadButton(k, flowPane, v, fileName);
                        });
                    });
                }

                flowPane.getChildren().add(button);
                flowPane.getChildren().add(textField);

            } else {
                System.out.println("Предмет с именем '" + itemPath + "' не найден.");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
