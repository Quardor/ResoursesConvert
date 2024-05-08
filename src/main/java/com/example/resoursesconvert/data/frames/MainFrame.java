package com.example.resoursesconvert.data.frames;

import com.example.resoursesconvert.Frame;
import com.example.resoursesconvert.ResourceConverter;
import com.example.resoursesconvert.LiteMaticaConverter;
import com.example.resoursesconvert.data.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.List;

public class MainFrame implements Frame {


    private final Stage stage;
    private Scene scene;

    @FXML
    public Button gpButton;
    @FXML
    public Button liteMaticaButton;

    private static final String IMAGE_DIRECTORY;

    static {
        try {
            IMAGE_DIRECTORY = ResourceConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "com\\example\\resoursesconvert\\textures";
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
    }

    public static void createLargeImagine(){
        java.util.List<String> imagePaths = getImagePaths(IMAGE_DIRECTORY);
        int gridWidth = calculateGridWidth(imagePaths.size());
        int gridHeight = calculateGridHeight(imagePaths.size(), gridWidth);

        BufferedImage gridImage = new BufferedImage(
                gridWidth * (IMAGE_SIZE + SPACING) - SPACING,
                gridHeight * (IMAGE_SIZE + SPACING) - SPACING,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = gridImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, gridImage.getWidth(), gridImage.getHeight());

        int x = 0;
        int y = 0;
        int id = 1;
        for (String imagePath : imagePaths) {
            try {
                BufferedImage image = javax.imageio.ImageIO.read(new File(imagePath));
                g2d.drawImage(image, x, y, IMAGE_SIZE, IMAGE_SIZE, null);
                g2d.setColor(Color.BLACK);
                g2d.drawString(Integer.toString(id), x, y + IMAGE_SIZE + 10); // Рисуем id рядом с изображением
                id++;
                images.put(id-1, imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            x += IMAGE_SIZE + SPACING;
            if (x >= gridImage.getWidth()) {
                x = 0;
                y += IMAGE_SIZE + SPACING;
            }
        }
        g2d.dispose();

        try {
            File outputImage = new File("grid_image.png");
            ImageIO.write(gridImage, "png", outputImage);
            System.out.println("Grid image saved to: " + outputImage.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

//        while (true) {
//            String inputString = scanner.nextLine();
//
//            if (inputString.equals("exit")) { System.exit(0); }
//            String path = images.get(Integer.parseInt(inputString));
//            if (path == null) {
//                System.out.println("Invalid image path: " + inputString);
//            } else {
//                System.out.println(path);
//            }
//        }
    }

    private static List<String> getImagePaths(String directoryPath) {
        List<String> imagePaths = new ArrayList<>();
        File directory = new File(directoryPath);
        addImagePaths(directory, imagePaths);
        return imagePaths;
    }

    private static void addImagePaths(File directory, List<String> imagePaths) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    imagePaths.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    addImagePaths(file, imagePaths); // Рекурсивный вызов для подпапки
                }
            }
        }
    }

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }

    private static int calculateGridWidth(int numberOfImages) {
        int gridWidth = (int) Math.ceil(Math.sqrt(numberOfImages));
        return gridWidth;
    }

    private static int calculateGridHeight(int numberOfImages, int gridWidth) {
        int gridHeight = (int) Math.ceil((double) numberOfImages / gridWidth);
        return gridHeight;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public Scene getScene() {
        return null;
    }
}
