package controllers;

import constants.CarConstants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import stageHandler.DialogBox;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;

public class StartController {

    @FXML
    private Button startBtn;

    @FXML
    private void startNewGame() throws IOException {
        Stage currentStage = (Stage) this.startBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage, CarConstants.CHOOSE_CAR_VIEW_PATH);
    }

    @FXML
    private void showHighScores() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(CarConstants.HIGH_SCORES_DIALOG));

        DialogBox.loadTableViewBox(fxmlLoader);
    }

    @FXML
    private void onClose() {
        Platform.exit();
    }
}
