package main;

import controllers.ScreenController;
import dataHandler.PlayerData;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ScreenController.getInstance().setPrimaryStage(primaryStage);
        ScreenController.getInstance().showLogin();
    }

    @Override
    public void init() throws Exception {
        PlayerData.getInstance().loadPlayersData();
    }

    @Override
    public void stop() {
        PlayerData.getInstance().storePlayersData();
        System.out.println("here to write");
    }
}
