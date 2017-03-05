package controllers;

import dataHandler.Constants;
import dataHandler.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import mapHandlers.Levels.FirstLevel;
import mapHandlers.Track;
import models.Player;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

public class ChooseLevelController {

    private static String level;
    public ImageView locked2;

    private Track track;

    @FXML
    private Button startGameBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML

    public void initialize() {
        track = new FirstLevel();
        Player p = PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        //showUnlockedCarsOnly(p.getHighScore());
    }

    public String getLevel() {
        return level;
    }

    private void setLevel(String level) {
        this.level = level;
    }

    public void startGame(ActionEvent actionEvent) {
        Stage currentStage = (Stage)this.startGameBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.START_FXML_PATH,null);
    }

    public void chooseLevel(MouseEvent ev) {
        Node source = (Node) ev.getSource();

        if (source.getId().substring(0, 5).equals("level")) {
            setLevel(source.getId());
            backgroundFill(source.getId().substring(source.getId().length() - 1));
        } else if (source.getId().substring(0, 5).equals("label")) {
            setLevel("car" + source.getId().substring(source.getId().length() - 1));
            backgroundFill(source.getId().substring(source.getId().length() - 1));
        }
        startGameBtn.setVisible(true);
    }

    private void showUnlockedCarsOnly(Long points) {
        backgroundBox1.setStyle(null);
        backgroundBox2.setStyle(null);

        backgroundBox2.setStyle("-fx-fill: rgba(95, 88, 93, 0.7);");
        backgroundBox2.toFront();
        locked2.setVisible(true);
    }

    private void backgroundFill(String id) {
        Player p = PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        showUnlockedCarsOnly(p.getHighScore());

        switch (id) {
            case "1":
                backgroundBox1.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                break;
            case "2":
                backgroundBox2.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                break;
        }
    }

}
