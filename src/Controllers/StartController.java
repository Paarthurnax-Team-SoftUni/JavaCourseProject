package Controllers;

import DataHandler.Player;
import DataHandler.PlayerData;
import MapHandlers.Track;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Main;

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

//        gameStarted = true;
//        setTime();
//        isGameRunning = true;
    }

    @FXML
    public void showHighScores() {
        showHighScoresDialog();
    }

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
