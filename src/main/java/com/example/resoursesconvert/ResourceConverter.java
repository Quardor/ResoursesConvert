package com.example.resoursesconvert;

import com.example.resoursesconvert.data.frames.LicenseFrame;
import com.example.resoursesconvert.data.frames.MainFrame;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;

public class ResourceConverter extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MainFrame.createLargeImagine();
        Frame mainFrame = new LicenseFrame(stage);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}