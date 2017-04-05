package controllers;

import constants.CarConstants;
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
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.lang.reflect.Field;

public class ChooseCarController {

    private static String carId;

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
    @FXML
    private ImageView locked1;
    @FXML
    private ImageView locked2;
    @FXML
    private ImageView locked3;
    @FXML
    private ImageView locked4;
    @FXML
    private ImageView locked5;
    @FXML
    private ImageView locked6;

    public void initialize() throws NoSuchFieldException, IllegalAccessException {
        long highScores = PlayerData.getInstance().getCurrentPlayer().getHighScore();
        showUnlockedCarsOnly(highScores);
    }

    @FXML
    public void goToChooseLevel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) this.goNextBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage, CarConstants.CHOOSE_LEVEL_VIEW_PATH);
    }

    public String getCarId() {
        return carId;
    }

    private void setCarId(String id) {
        carId = id;
    }

    public void chooseCar(MouseEvent ev) throws NoSuchFieldException, IllegalAccessException {
        Node source = (Node) ev.getSource();
        String id = source.getId();
        backgroundFill(id.substring(id.length() - 1));
        if (id.substring(0, 3).equals(CarConstants.CAR_STRING)) {
            this.setCarId(id);
        } else if (source.getId().substring(0, 5).equals("label")) {
            this.setCarId(CarConstants.CAR_STRING + id.substring(id.length() - 1));
        }
        this.goNextBtn.setVisible(true);
    }

    private void showUnlockedCarsOnly(Long points) throws NoSuchFieldException, IllegalAccessException {
        Class<ChooseCarController> chooseCarControllerClass = ChooseCarController.class;
        for (int id = 1; id <= 6; id++) {
            Field ellipseField = chooseCarControllerClass.getDeclaredField("backgroundBox" + id);
            Ellipse ellipse = ((Ellipse) ellipseField.get(this));
            ellipse.setStyle(null);
            Field lockedField = chooseCarControllerClass.getDeclaredField("locked" + id);
            ImageView locked = ((ImageView) lockedField.get(this));
            locked.setVisible(false);
            if (points < (id - 1) * CarConstants.CAR_UNLOCK_STEP_PTS) {
                ellipse.setStyle(CarConstants.GREY_COLOUR);
                ellipse.toFront();
                locked.setVisible(true);
            }
        }
    }

    private void backgroundFill(String id) throws NoSuchFieldException, IllegalAccessException {
        long highScores = PlayerData.getInstance().getCurrentPlayer().getHighScore();
        showUnlockedCarsOnly(highScores);

        Class<ChooseCarController> chooseCarControllerClass = ChooseCarController.class;
        Field field = chooseCarControllerClass.getDeclaredField("backgroundBox" + id);
        ((Ellipse) field.get(this)).setStyle(CarConstants.RED_COLOUR);

    }
}