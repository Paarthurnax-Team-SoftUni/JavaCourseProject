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
import models.Player;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

public class ChooseCarController{

    private static String carId;
    public ImageView locked2;
    public ImageView locked3;
    public ImageView locked4;
    public ImageView locked5;
    public ImageView locked6;

    private Player currentPlayer;

    @FXML
    private Button goNextBtn;
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
        this.setCurrentPlayer(PlayerData.getInstance().getCurrentPlayer());
        showUnlockedCarsOnly(this.getCurrentPlayer().getHighScore());
    }

    public String getCarId() {
        return carId;
    }

    private void setCarId(String carId) {
        this.carId = carId;
    }

    private Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void goToChooseLevel(ActionEvent actionEvent) {
        Stage currentStage = (Stage)this.goNextBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.CHOOSE_LEVEL_VIEW_PATH,null);
    }

    public void chooseCar(MouseEvent ev) {
        Node source = (Node) ev.getSource();
        String id = source.getId();
        backgroundFill(id.substring(id.length() - 1));
        if (id.substring(0, 3).equals(Constants.CAR_STRING)) {
            this.setCarId(id);
        } else if (source.getId().substring(0, 5).equals("label")) {
            this.setCarId(Constants.CAR_STRING + id.substring(id.length() - 1));
        }
        this.goNextBtn.setVisible(true);
    }

    private void showUnlockedCarsOnly(Long points) {
        this.backgroundBox1.setStyle(null);
        this.backgroundBox2.setStyle(null);
        this.backgroundBox3.setStyle(null);
        this.backgroundBox4.setStyle(null);
        this.backgroundBox5.setStyle(null);
        this.backgroundBox6.setStyle(null);

        this.backgroundBox2.setStyle(Constants.GREY_COLOUR);
        this.backgroundBox2.toFront();
        this.locked2.setVisible(true);
        this.backgroundBox3.setStyle(Constants.GREY_COLOUR);
        this.backgroundBox3.toFront();
        this.locked3.setVisible(true);
        this.backgroundBox4.setStyle(Constants.GREY_COLOUR);
        this.backgroundBox4.toFront();
        this.locked4.setVisible(true);
        this.backgroundBox5.setStyle(Constants.GREY_COLOUR);
        this.backgroundBox5.toFront();
        this.locked5.setVisible(true);
        this.backgroundBox6.setStyle(Constants.GREY_COLOUR);
        this.backgroundBox6.toFront();
        this.locked6.setVisible(true);

        if (points > 10000) {
            this.backgroundBox2.setStyle(null);
            this.backgroundBox2.toBack();
            this.locked2.setVisible(false);
        }
        if (points > 15000) {
            this.backgroundBox3.setStyle(null);
            this.backgroundBox3.toBack();
            this.locked3.setVisible(false);
        }
        if (points > 22000) {
            this.backgroundBox4.setStyle(null);
            this.backgroundBox4.toBack();
            this.locked4.setVisible(false);
        }
        if (points > 35000) {
            this.backgroundBox5.setStyle(null);
            this.backgroundBox5.toBack();
            this.locked5.setVisible(false);
        }
        if (points > 50000) {
            this.backgroundBox6.setStyle(null);
            this.backgroundBox6.toBack();
            this.locked6.setVisible(false);
        }
    }

    private void backgroundFill(String id) {
        this.showUnlockedCarsOnly(this.getCurrentPlayer().getHighScore());

        switch (id) {
            case "1":
                this.backgroundBox1.setStyle(Constants.RED_COLOUR);
                break;
            case "2":
                this.backgroundBox2.setStyle(Constants.RED_COLOUR);
                break;
            case "3":
                this.backgroundBox3.setStyle(Constants.RED_COLOUR);
                break;
            case "4":
                this.backgroundBox4.setStyle(Constants.RED_COLOUR);
                break;
            case "5":
                this.backgroundBox5.setStyle(Constants.RED_COLOUR);
                break;
            case "6":
                this.backgroundBox6.setStyle(Constants.RED_COLOUR);
                break;
        }
    }
}