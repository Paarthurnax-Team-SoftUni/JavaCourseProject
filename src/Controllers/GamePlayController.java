package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GamePlayController {
    public AnchorPane gamePlayPage;
    public AnchorPane menu;
    public Button pauseBtn;
    public Button quitBtn;
    public static Label timeInfo;
    public static Label scorePoints;

    public void pauseGame(ActionEvent actionEvent) {

    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
