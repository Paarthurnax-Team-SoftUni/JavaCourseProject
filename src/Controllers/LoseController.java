package Controllers;

import GameLogic.Game;
import MapHandlers.Track;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static Controllers.ScreenController.*;

public class LoseController {

    @FXML
    public Button newGameBtn;
    @FXML
    public Button quitBtn;
    @FXML
    public AnchorPane gameOverPage;

    public void restartGame(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        Game.clearObstaclesAndCollectibles();
        loadStage(stage, startStage, "../views/start.fxml");
//        closeStage(stage);
//        Track.initializeLevel(1);
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
