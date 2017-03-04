package controllers;

import dataHandler.Constants;
import dataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Player;

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
        Stage currentStage = (Stage) loginBtn.getScene().getWindow();

        if ("".equals(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("You have to fill in the username field!");
            alert.setContentText("Please click on OK to retry!");
            alert.setHeaderText("You have to fill in the username field!");
            alert.showAndWait();
        } else if (!PlayerData.getInstance().checkForPlayer(name)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login with this username");
            alert.setHeaderText("Login as user: " + name);
            alert.setContentText("Are you sure? Press OK to continue, or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                PlayerData.getInstance().setCurrentPlayer(PlayerData.getInstance().returnPlayer(name));
                // Track.getRunTrack().setPlayer(PlayerData.getInstance().returnPlayer(name));
                ScreenController.getInstance().loadStage(currentStage, ScreenController.getInstance().getStartStage(), Constants.START_FXML_PATH);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Create new user");
            alert.setHeaderText("Create new user: " + name);
            alert.setContentText("Are you sure? Press OK to continue, or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Player player = new Player(name, 0L, 0.0, 0L, 0L, 100);

                //  Track.getRunTrack().setPlayer(player);

                PlayerData.getInstance().addPlayer(player);
                PlayerData.getInstance().storePlayersData();
                PlayerData.getInstance().setCurrentPlayer(PlayerData.getInstance().returnPlayer(name));
                
                ScreenController.getInstance().loadStage(currentStage, ScreenController.getInstance().getStartStage(), Constants.START_FXML_PATH);


            }
        }
    }
}