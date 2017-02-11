package Controllers;

import GameLogic.Game;
import javafx.scene.layout.AnchorPane;
import main.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.stage.Stage;

import javax.accessibility.AccessibleComponent;

import java.io.IOException;

import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class LoseController {

    @FXML
    public Button newGameBtn;
    @FXML
    public Button quitBtn;
    @FXML
    public AnchorPane gameOverPage;

    public void restartGame(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        Game.clearObs();
        loadStage(stage, startStage, "../views/start.fxml");
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
