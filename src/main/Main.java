package main;

import Controllers.ScreenController;
import DataHandler.PlayerData;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final String TITLE = "Race Game";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ScreenController.setPrimaryStage(primaryStage);
        ScreenController.showLogin();
    }

    @Override
    public void init() throws Exception {
        PlayerData.getInstance().loadPlayersData();
    }

    @Override
    public void stop() {
//        PlayerData.getInstance().storePlayersData();
    }
}
