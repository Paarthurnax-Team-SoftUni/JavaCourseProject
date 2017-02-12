package Controllers;

import DataHandler.Player;
import DataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class LoginController implements Initializable{

    public static Player player;

    @FXML
    public AnchorPane loginPage;
    @FXML
    public TextField playerName;
    @FXML
    public Button loginBtn;

    @FXML
    private void showStartPage() throws IOException {

        String name = playerName.getText().trim();
        Stage currentStage = (Stage) loginBtn.getScene().getWindow();

        if ("".equals(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("You have to fill in the username field!");
            alert.setContentText("Please click on OK to retry!");
            alert.setHeaderText("You have to fill in the username field!");
            Optional<ButtonType> result = alert.showAndWait();
        } else if (!PlayerData.getInstance().checkForPlayer(name)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login with this username");
            alert.setHeaderText("Login as user: " + name);
            alert.setContentText("Are you sure? Press OK to continue, or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                player = PlayerData.getInstance().returnPlayer(name);
                player.setHealthPoints(100);
                loadStage(currentStage, startStage, "../views/start.fxml");

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Create new user");
            alert.setHeaderText("Create new user: " + name);
            alert.setContentText("Are you sure? Press OK to continue, or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                player = new Player(name, 0L, 0.0, 0L, 0L, 100);

                /* remove the comments to save every newly created player*/
                PlayerData.getInstance().addPlayer(player);
                PlayerData.getInstance().storePlayersData();

                loadStage(currentStage, startStage, "../views/start.fxml");

            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
