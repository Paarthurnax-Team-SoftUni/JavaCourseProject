package Controllers;

import GameLogic.Game;
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


/**
 * Created by Toster on 2/10/2017.
 */
public class LoseController {
    @FXML
    public Button yesButton;

    @FXML
    Button noButton;
    @FXML
    private Stage scene;

    public void onYes(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        Game.clearObs();
        loadStage(stage, startStage, "../views/start.fxml");
        stage.close();
    }

    public void onNo(ActionEvent actionEvent) {
        Platform.exit();
    }


}
