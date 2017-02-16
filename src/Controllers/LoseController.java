package Controllers;

import DataHandler.Constants;
import GameLogic.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class LoseController {

    @FXML
    private Button quitBtn;

    public void restartGame(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        Game.clearObstaclesAndCollectibles();
        loadStage(stage, startStage, Constants.START_FXML_PATH);
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
