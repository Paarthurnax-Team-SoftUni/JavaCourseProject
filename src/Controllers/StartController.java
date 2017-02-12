package Controllers;

import DataHandler.CurrentTime;
import DataHandler.Player;
import DataHandler.CurrentPoints;
import GameLogic.Game;
import MapHandlers.Track;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import static Controllers.ScreenController.chooseCarStage;
import static Controllers.ScreenController.loadStage;

public class StartController {

    public static Player player;

    @FXML
    public AnchorPane homePage;
    @FXML
    public Button startBtn;
    @FXML
    public Button showScoresBtn;
    @FXML
    public Button chooseCarBtn;
    @FXML
    public Button closeBtn;
    @FXML
    public Rectangle backgroundBox;
    @FXML
    public Button returnBtn;

    @FXML
    public Label Score;

    @FXML
    public Label scorePoints;
    @FXML
    public Label timeInfo;
    @FXML
    public Label time;

    @FXML
    public void chooseCar() throws IOException {
        Stage currentStage = (Stage) startBtn.getScene().getWindow();
        loadStage(currentStage, chooseCarStage, "../views/chooseCar.fxml");
    }

    @FXML
    public void startNewGame() {

        Track.initializeLevel(1);
        showScoresBtn.setVisible(false);
        startBtn.setVisible(false);
        closeBtn.setVisible(false);
        chooseCarBtn.setVisible(false);
        backgroundBox.setVisible(false);
        Score.setVisible(true);
        scorePoints.setVisible(true);
        timeInfo.setVisible(true);
        time.setVisible(true);

        CurrentPoints currentPlayerPoints=  Game.getCurrentPoints();
        CurrentTime currentTime=Game.getCurrentTime();
        scorePoints.textProperty().bind(Bindings.convert(currentPlayerPoints.valueProperty()));
        timeInfo.textProperty().bind(Bindings.convert(currentTime.valueProperty()));
//        gameStarted = true;
//        setTime();
//        isGameRunning = true;
    }

    @FXML
    public void showHighScores() {
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
    public void onClose() {
        Stage currentStage = (Stage) startBtn.getScene().getWindow();
        currentStage.close();
    }
}
