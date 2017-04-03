package main;

import constants.CarConstants;
import dataHandler.PlayerData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StageManager manager = new StageManagerImpl();
        manager.loadSceneToStage(primaryStage, CarConstants.LOGIN_VIEW_PATH);
    }

    @Override
    public void init() throws Exception {
        super.init();
        PlayerData.getInstance().createDb();
        if(!PlayerData.getInstance().open()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(CarConstants.DB_INIT_ERROR_TITLE);
            alert.setContentText(CarConstants.DB_INIT_ERROR_CONTENT);
            Platform.exit();
        }
        PlayerData.getInstance().loadPlayersData();
    }

    @Override
    public void stop() throws SQLException {
        PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());
        PlayerData.getInstance().close();
    }
}
