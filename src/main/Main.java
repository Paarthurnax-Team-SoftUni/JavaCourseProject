package main;

import dataHandler.Constants;
import dataHandler.PlayerData;
import javafx.application.Application;
import javafx.stage.Stage;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StageManager manager = new StageManagerImpl();
        manager.loadSceneToStage(primaryStage, Constants.LOGIN_VIEW_PATH,null);
    }

    @Override
    public void init() throws Exception {
        PlayerData.getInstance().loadPlayersData();
    }

    @Override
    public void stop() {
        PlayerData.getInstance().storePlayersData();
    }
}
