package controllers;

import dataHandler.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import mapHandlers.Track;

import java.io.IOException;

public class StartController {
    @FXML
    private Button startBtn;

    @FXML
    private void chooseCar() throws IOException {
        Stage currentStage = (Stage) startBtn.getScene().getWindow();
        ScreenController.getInstance().loadStage(currentStage, ScreenController.getInstance().getChooseCarStage(), Constants.CHOOSE_CAR_VIEW_PATH);

    }

    @FXML

    private void startNewGame() throws IOException {
        Stage currentStage = (Stage) startBtn.getScene().getWindow();
        ScreenController.getInstance().loadStage(currentStage, ScreenController.getInstance().getGamePlayStage(), Constants.GAME_PLAY_VIEW_PATH);
        try {
            Track.initializeLevel(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showHighScores() {
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
