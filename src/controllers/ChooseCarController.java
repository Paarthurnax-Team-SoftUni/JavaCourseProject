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
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import mapHandlers.Levels.FirstLevel;
import mapHandlers.Track;
import models.Player;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;

public class ChooseCarController{

    private static String carId;
    public ImageView locked2;
    public ImageView locked3;
    public ImageView locked4;
    public ImageView locked5;
    public ImageView locked6;

    private Track track;

    @FXML
    private Button returnBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML
    private Ellipse backgroundBox3;
    @FXML
    private Ellipse backgroundBox4;
    @FXML
    private Ellipse backgroundBox5;
    @FXML
    private Ellipse backgroundBox6;

    public void initialize() {
        track = new FirstLevel();
        Player p = PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        showUnlockedCarsOnly(p.getHighScore());
    }

    public String getCarId() {
        return carId;
    }

    private void setCarId(String carId) {
        this.carId = carId;
    }

    public void renderStartMenu(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage)this.returnBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage,Constants.START_FXML_PATH,null);
    }

    public void chooseCar(MouseEvent ev) {
        Node source = (Node) ev.getSource();

        if (source.getId().substring(0, 3).equals("car")) {
            setCarId(source.getId());
            backgroundFill(source.getId().substring(source.getId().length() - 1));
        } else if (source.getId().substring(0, 5).equals("label")) {
            setCarId("car" + source.getId().substring(source.getId().length() - 1));
            backgroundFill(source.getId().substring(source.getId().length() - 1));
        }
        returnBtn.setVisible(true);
    }

    private void showUnlockedCarsOnly(Long points) {
        backgroundBox1.setStyle(null);
        backgroundBox2.setStyle(null);
        backgroundBox3.setStyle(null);
        backgroundBox4.setStyle(null);
        backgroundBox5.setStyle(null);
        backgroundBox6.setStyle(null);

        backgroundBox2.setStyle("-fx-fill: rgba(95, 88, 93, 0.7);");
        backgroundBox2.toFront();
        locked2.setVisible(true);
        backgroundBox3.setStyle("-fx-fill: rgba(95, 88, 93, 0.7);");
        backgroundBox3.toFront();
        locked3.setVisible(true);
        backgroundBox4.setStyle("-fx-fill: rgba(95, 88, 93, 0.7);");
        backgroundBox4.toFront();
        locked4.setVisible(true);
        backgroundBox5.setStyle("-fx-fill: rgba(95, 88, 93, 0.7);");
        backgroundBox5.toFront();
        locked5.setVisible(true);
        backgroundBox6.setStyle("-fx-fill: rgba(95, 88, 93, 0.7);");
        backgroundBox6.toFront();
        locked6.setVisible(true);

        if (points > 10000) {
            backgroundBox2.setStyle(null);
            backgroundBox2.toBack();
            locked2.setVisible(false);
        }
        if (points > 15000) {
            backgroundBox3.setStyle(null);
            backgroundBox3.toBack();
            locked3.setVisible(false);
        }
        if (points > 22000) {
            backgroundBox4.setStyle(null);
            backgroundBox4.toBack();
            locked4.setVisible(false);
        }
        if (points > 35000) {
            backgroundBox5.setStyle(null);
            backgroundBox5.toBack();
            locked5.setVisible(false);
        }
        if (points > 50000) {
            backgroundBox6.setStyle(null);
            backgroundBox6.toBack();
            locked6.setVisible(false);
        }
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
            case "3":
                backgroundBox3.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                break;
            case "4":
                backgroundBox4.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                break;
            case "5":
                backgroundBox5.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                break;
            case "6":
                backgroundBox6.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                break;
        }
    }
}