package com.example.resoursesconvert.data.frames;

import com.example.resoursesconvert.Frame;
import com.example.resoursesconvert.ResourceConverter;
import com.example.resoursesconvert.data.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class ChangeCraftFrame implements Frame {

    public Button backButton;
    @FXML
    public StackPane stackPane;

    private final Stage stage;
    private final HashMap<Integer, String> images;

    public ChangeCraftFrame(Stage stage, HashMap<Integer, String> images) {
        this.stage = stage;
        this.images = images;
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
        Yaml yaml = new Yaml();

        List<Button> buttons = new ArrayList<>();

        try {

            String filePath = ResourceConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\data\\items\\items.yml";

            FileInputStream fis = new FileInputStream(filePath);

            Map<String, Map<String, Map<String, String>>> data = yaml.load(fis);

            Map<String, Map<String, String>> items = data.get("items");

            backButton = new Button(" ");
            backButton.getStyleClass().add("backButton");


            for (Map.Entry<String, Map<String, String>> entry : items.entrySet()) {
                String itemName = entry.getKey();
                Map<String, String> itemDetails = entry.getValue();

                if (itemDetails.get("craft") != null) {

                    Button button = new Button();

                    Image image = new Image(images.get(itemDetails.get("icon")));

                    ImageView imageView = new ImageView(image);

                    button.setGraphic(imageView);
                    button.setPrefSize(50, 50);

                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);

                    imageView.setPreserveRatio(true);

                    button.setGraphic(imageView);

                    button.setOnAction(event -> {
                        TextInputDialog dialog = new TextInputDialog("1");
                        dialog.setTitle("Введите количество");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Количество предметов:");

                        Optional<String> result = dialog.showAndWait();

                        String value = result.orElse("");

                        if(value.isEmpty()) return;

                        if(CraftFrame.frame == null) {
                            CraftFrame.frame = new CraftFrame(stage, itemDetails.get("craft"), images, new HashMap<>(), Integer.parseInt(value));
                            CraftFrame.frame.setVisible(true);
                        }else{
                            CraftFrame.frame.setVisible(true);
                        }
                    });
                    buttons.add(button);
                }
            }

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setMaxHeight(541);
            scrollPane.setMaxWidth(1080);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background: rgba(0,0,0,0);");

            FlowPane newFlowPane = new FlowPane();
            newFlowPane.setHgap(10);
            newFlowPane.setVgap(10);
            newFlowPane.getChildren().addAll(buttons);

            scrollPane.setContent(newFlowPane);

            stackPane.getChildren().add(scrollPane);
            stackPane.setAlignment(scrollPane, Pos.TOP_CENTER);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        stackPane.getChildren().add(backButton);

        backButton.setOnAction(actionEvent -> {
            Frame frame = new MainFrame(stage);
            frame.setVisible(true);
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
}
