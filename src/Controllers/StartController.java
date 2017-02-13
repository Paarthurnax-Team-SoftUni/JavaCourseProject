package Controllers;

import MapHandlers.Track;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import static Controllers.ScreenController.chooseCarStage;
import static Controllers.ScreenController.gamePlayStage;
import static Controllers.ScreenController.loadStage;

public class StartController {

    @FXML
    private AnchorPane homePage;
    @FXML
    private Button startBtn;
    @FXML
    private Button showScoresBtn;
    @FXML
    private Button chooseCarBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Rectangle backgroundBox;

    @FXML
    private void chooseCar() throws IOException {
        Stage currentStage = (Stage) startBtn.getScene().getWindow();
        loadStage(currentStage, chooseCarStage, "../views/chooseCar.fxml");

    }

    @FXML

    private void startNewGame() throws IOException {
        Stage currentStage = (Stage) startBtn.getScene().getWindow();
        loadStage(currentStage, gamePlayStage, "../views/gamePlay.fxml");
        try {
            Track.initializeLevel(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        gameStarted = true;
//        setTime();
//        isGameRunning = true;
    }

    @FXML
    private void showHighScores() {
        showHighScoresDialog();
    }

    @FXML
    private void showHighScoresDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Best Slav Ranking");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/highScoresDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Can't load the High Scores dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    @FXML
    private void onClose() {
        Platform.exit();
    }
}
