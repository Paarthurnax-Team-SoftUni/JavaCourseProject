package controllers;

import dataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Player;
import utils.constants.GameplayConstants;
import utils.constants.ViewsConstants;
import utils.stages.DialogBox;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField playerName;
    @FXML
    private Button loginBtn;

    @FXML
    private void showStartPage() throws IOException, SQLException {
        Stage currentStage = (Stage) this.loginBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();

        String name = this.playerName.getText().trim();

        if ("".equals(name)) {
            DialogBox.loadErrorBox();
        } else if (!PlayerData.getInstance().checkForPlayer(name)) {
            boolean result = DialogBox.loadConfirmBox(name);
            if (result) {
                PlayerData.getInstance().registerPlayer(PlayerData.getInstance().returnPlayer(name));
                manager.loadSceneToStage(currentStage, ViewsConstants.START_FXML_PATH);
            }
        } else {
            boolean result = DialogBox.loadConfirmBox(name);
            if (result) {
                Player player = new Player(name, GameplayConstants.INITIAL_SCORE, GameplayConstants.INITIAL_MONEY, GameplayConstants.HEALTH_BAR_MAX);
                PlayerData.getInstance().addPlayer(player);
                PlayerData.getInstance().storePlayersData(player);
                PlayerData.getInstance().registerPlayer(PlayerData.getInstance().returnPlayer(name));
                PlayerData.getInstance().updatePlayer(player);

                manager.loadSceneToStage(currentStage, ViewsConstants.START_FXML_PATH);
            }
        }
    }
}