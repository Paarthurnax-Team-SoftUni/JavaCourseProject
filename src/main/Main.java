package main;

import dataHandler.ModelsParamsManager;
import dataHandler.PlayerData;
import dataHandler.TrackParams;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import utils.constants.ErrorConstants;
import utils.constants.ViewsConstants;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        StageManager manager = new StageManagerImpl();
        manager.loadSceneToStage(primaryStage, ViewsConstants.LOGIN_VIEW_PATH);
    }

    @Override
    public void init() throws Exception {
        super.init();
        PlayerData.getInstance().createDb();
        if (!PlayerData.getInstance().open()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ErrorConstants.DB_INIT_ERROR_TITLE);
            alert.setContentText(ErrorConstants.DB_INIT_ERROR_CONTENT);
            Platform.exit();
        }
        PlayerData.getInstance().loadPlayersData();
        TrackParams.getInstance();
        ModelsParamsManager.getInstance().initializeModelConstants();
    }

    @Override
    public void stop() throws SQLException {
        if (PlayerData.getInstance().getCurrentPlayer() != null) {
            PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());
            PlayerData.getInstance().close();
        }
    }
}
