package controllers;

import constants.ViewsConstants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;

public class LoseController {

    @FXML
    private Button quitBtn;

    public void restartGame(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) this.quitBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        manager.loadSceneToStage(currentStage, ViewsConstants.START_FXML_PATH);
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
