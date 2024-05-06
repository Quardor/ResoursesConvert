package com.example.resoursesconvert;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public interface Frame {

    void setVisible(boolean b);

    void onInitialize();

    Stage getStage();

    Scene getScene();

    static void switchToScene(Scene scene, Scene newScene, Stage stage) {
        if (scene != null) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), scene.getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                stage.setScene(newScene);
                fadeInTransition(newScene.getRoot());
                stage.show();
            });
            fadeOut.play();
        } else {
            stage.setScene(newScene);
            fadeInTransition(newScene.getRoot());
            stage.show();
        }
    }

    private static void fadeInTransition(Parent root) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

}
