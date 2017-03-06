package controllers;

import dataHandler.Constants;
import dataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Player;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;
import java.util.Optional;

public class LoginController{

    @FXML
    private TextField playerName;
    @FXML
    private Button loginBtn;

    @FXML
    private void showStartPage() throws IOException {

        String name = playerName.getText().trim();
        Stage currentStage = (Stage) this.loginBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();

        if ("".equals(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Constants.ERROR_USERNAME_TITLE);
            alert.setContentText(Constants.ERROR_USERNAME_CONTENT);
            alert.setHeaderText(Constants.ERROR_USERNAME_HEADER);
            alert.showAndWait();
        } else if (!PlayerData.getInstance().checkForPlayer(name)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(Constants.LOGIN_USER_TITLE);
            alert.setHeaderText(Constants.LOGIN_USER_HEADER + name);
            alert.setContentText(Constants.LOGIN_USER_CONTENT);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                PlayerData.getInstance().setCurrentPlayer(PlayerData.getInstance().returnPlayer(name));
                // Track.getRunTrack().setPlayer(PlayerData.getInstance().returnPlayer(name));
                FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.START_FXML_PATH,null);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(Constants.CREATE_USER_TITLE);
            alert.setHeaderText(Constants.CREATE_USER_HEADER + name);
            alert.setContentText(Constants.CREATE_USER_CONTENT);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Player player = new Player(name, 0L, 0.0, 0L, 0L, 100);

                //  Track.getRunTrack().setPlayer(player);

                PlayerData.getInstance().addPlayer(player);
                PlayerData.getInstance().storePlayersData();
                PlayerData.getInstance().setCurrentPlayer(PlayerData.getInstance().returnPlayer(name));

                FXMLLoader loader = manager.loadSceneToStage(currentStage,Constants.START_FXML_PATH,null);
            }
        }
    }
}