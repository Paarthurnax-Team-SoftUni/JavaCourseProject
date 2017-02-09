package Controllers;

import DataHandler.Player;
import DataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;

public class ChooseCarController {

    public void initialize() {
        try {
            File file = new File("E:/Dev/JavaCourseProject/src/resources/images/background-home-1.jpg");
            String localUrl = file.toURI().toURL().toString();
            // don't load in the background
            Image localImage = new Image(localUrl, false);

            String remoteUrl = "http://java2s.com/style/demo/Firefox.png";
            // load in the background
            Image remoteImage = new Image(remoteUrl, true);

            System.out.println(localUrl);
            System.out.println(remoteUrl);

        } catch (MalformedURLException ex) {
            // error
        }
    }
}
