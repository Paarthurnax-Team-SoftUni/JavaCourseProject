package controllers;

import GameEngine.GameLoop;
import GameEngine.GamePlayController;
import dataHandler.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoseController {

    @FXML
    private Button quitBtn;

    public void restartGame(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        GamePlayController.getInstance().clearObstaclesAndCollectibles();
        ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getStartStage(), Constants.START_FXML_PATH);
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }





}
