package controllers;

import dataHandler.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;

public class WinController {

    @FXML
    public Button quitBtn;

    public void restartGame(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage)this.quitBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage,Constants.START_FXML_PATH,null);
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
