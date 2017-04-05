package controllers;

import models.Player;
import stageHandler.DialogBox;
import constants.CarConstants;
import dataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController{

    @FXML
    private TextField playerName;
    @FXML
    private Button loginBtn;

    @FXML
    private void showStartPage() throws IOException, SQLException {
        Stage currentStage = (Stage) this.loginBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();

        String name = playerName.getText().trim();

        if ("".equals(name)) {
            DialogBox.loadErrorBox();
        } else if (!PlayerData.getInstance().checkForPlayer(name)) {
            boolean result = DialogBox.loadConfirmBox(name);

            if (result) {
                PlayerData.getInstance().registerPlayer(PlayerData.getInstance().returnPlayer(name));

                FXMLLoader loader = manager.loadSceneToStage(currentStage, CarConstants.START_FXML_PATH);
            }
        } else {
            boolean result = DialogBox.loadConfirmBox(name);

            if (result) {
                Player player = new Player(name, 0L, 0.0, 100);
                PlayerData.getInstance().addPlayer(player);
                PlayerData.getInstance().storePlayersData(player);
                PlayerData.getInstance().registerPlayer(PlayerData.getInstance().returnPlayer(name));
                PlayerData.getInstance().updatePlayer(player);

                FXMLLoader loader = manager.loadSceneToStage(currentStage, CarConstants.START_FXML_PATH);
            }
        }
    }
}