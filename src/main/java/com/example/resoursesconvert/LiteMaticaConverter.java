package com.example.resoursesconvert;

import com.example.resoursesconvert.data.Resources;
import com.example.resoursesconvert.data.frames.CraftFrame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class LiteMaticaConverter implements Frame {

    @FXML
    public StackPane stackPane;

    private Stage stage;
    private String filePath;
    private HashMap<String, Integer> resources;
    private FlowPane flowPane;

    public LiteMaticaConverter(String filePath, Stage stage) {
        this.filePath = filePath;
        this.stage = stage;
        this.resources = new HashMap<>();
        this.flowPane = new FlowPane();
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
        }
        else {
            stage.hide();
        }
    }

    @Override
    public void onInitialize() {

    }

    @FXML
    public void initialize(){
        Yaml yaml = new Yaml();
        FileInputStream fis = null;
        try {
            resources = LiteMaticaConverter(new InputStreamReader(new FileInputStream(filePath), "CP1251"));
            filePath = ResourceConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\data\\items\\litematics_items.yml";
            fis = new FileInputStream(filePath);
        } catch (UnsupportedEncodingException | FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Map<String, Map<String, Map<String, String>>> data = yaml.load(fis);

        resources.keySet().forEach(key ->{
            System.out.println(key);
            int count = resources.get(key);
            CraftFrame.loadButton(key, flowPane, count, "litematics_items.yml");
        });

        stackPane.getChildren().add(flowPane);

    }

    public static HashMap<String, Integer> LiteMaticaConverter(InputStreamReader file) {

        HashMap<String, Integer> resources = new HashMap<>();

        BufferedReader reader = null;
        reader = new BufferedReader(file);

        List<String> lines = new ArrayList<>(reader.lines().toList());

        lines.subList(0, 5).clear();
        lines.subList(lines.size() - 3, lines.size()).clear();

        lines.forEach(line -> {
            resources.put(getResourceName(line), getResourceIsMissing(line));
        });

        return resources;
    }

    public static String getResourceName(String line) {

        StringBuilder resourceName = new StringBuilder();

        int spaceIndex = 0;

        for(int i = 2; i < line.length(); i++) {
            if(i == spaceIndex+1 && line.charAt(i) == ' ') break;

            if (line.charAt(i) == ' ') {
                spaceIndex = i;
            }
            resourceName.append(line.charAt(i));
        }
        return resourceName.toString().substring(0, resourceName.toString().length() - 1);
    }

    public static int getResourceIsMissing(String line) {
        int countSimv = 0;
        int simvIndex = 0;
        for(int i = 2; i < line.length(); i++) {
            if(line.charAt(i) == '|') countSimv++;
            if(countSimv == 2) {
                simvIndex = i;
                break;
            }
        }
        int spaceIndex = 0;
        StringBuilder resourceCount = new StringBuilder();
        for(int i = simvIndex+1; simvIndex < line.length(); i++) {
            if(line.charAt(i) == '|') break;
            resourceCount.append(line.charAt(i));
        }
        return Integer.parseInt(resourceCount.toString().trim());
    }

    public static HashMap<String, Integer> resourcesToLight(HashMap<String, Integer> resources) {
        Yaml yaml = new Yaml();
        FileInputStream fis = null;
        String filePath = null;
        try {
            filePath = ResourceConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\data\\items\\litematics_items.yml";
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Map<String, Map<String, Map<String, String>>> data = yaml.load(fis);

        Map<String, Map<String, String>> items = data.get("items");

        HashMap<String, Integer> lightResources = new HashMap<>();

        resources.keySet().forEach(resource -> {
            if (data.containsKey(resource)) {
                Map<String, Map<String, String>> item = data.get(resource);
                if(item.get("craft") != null){

                }
            }else System.out.println(resource + " отсутствует в конфиге");
        });
        return lightResources;
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

