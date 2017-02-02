package Controllers;

import DataHandler.Player;
import DataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class DialogController {

    @FXML
    private TextField playerNameField;

    public Player processResults() {
        String name = playerNameField.getText().trim();
        Player player = new Player(name, 0L , 0.0, 0L, 0L, 100);
        if(PlayerData.getInstance().checkForPlayer(player)) {
            PlayerData.getInstance().addPlayer(player);
            PlayerData.getInstance().storePlayersData();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player Name exists!");
            alert.setHeaderText(null);
            alert.setContentText("Please add player with another name!");
            alert.showAndWait();
        }
        return player;

    }
}
