package com.example.resoursesconvert.data;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Resources {

    private final String jarPath;

    public Resources(){
        try {
            jarPath = Resources.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getFXML(String fxml) {
        try {
            return new URL("file:" + jarPath + "com/example/resoursesconvert/" + fxml + ".fxml");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
