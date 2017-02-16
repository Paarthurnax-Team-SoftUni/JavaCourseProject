package controllers;

import dataHandler.Constants;
import mapHandlers.Track;
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

import static controllers.ScreenController.chooseCarStage;
import static controllers.ScreenController.gamePlayStage;
import static controllers.ScreenController.loadStage;

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
        loadStage(currentStage, chooseCarStage, Constants.CHOOSE_CAR_VIEW_PATH);

    }

    @FXML

    private void startNewGame() throws IOException {
        Stage currentStage = (Stage) startBtn.getScene().getWindow();
        loadStage(currentStage, gamePlayStage, Constants.GAME_PLAY_VIEW_PATH);
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
        dialog.setTitle(Constants.HIGH_SCORE_DIALOG_TITLE);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(Constants.HIGH_SCORES_DIALOG));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(Constants.DIALOG_MESSAGE);
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
