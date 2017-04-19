package controllers;

import dataHandler.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import mapHandlers.TrackMode;
import utils.constants.GameplayConstants;
import utils.constants.ImagesShortcutConstants;
import utils.constants.StylesConstants;
import utils.constants.ViewsConstants;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.io.IOException;
import java.lang.reflect.Field;

public class ChooseModeController {

    @FXML
    private Button goNextBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML
    private Ellipse backgroundBox3;

    public void initialize() throws IOException {
    }

    @FXML
    private void goToChooseLevel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) this.goNextBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage, ViewsConstants.CHOOSE_LEVEL_VIEW_PATH);
    }

    @FXML
    private void chooseMode(MouseEvent ev) throws IOException, NoSuchFieldException, IllegalAccessException {
        Node source = (Node) ev.getSource();
        int id = Integer.valueOf(source.getId().substring(source.getId().length()-1));
        this.backgroundFill(id);
        PlayerData.getInstance().getCurrentPlayer().updateCurrentTrackmode(TrackMode.values()[id-1]);
        this.goNextBtn.setVisible(true);
    }

    private void backgroundFill(int id) throws IOException, NoSuchFieldException, IllegalAccessException {
        Class<ChooseModeController> chooseModeControllerClass = ChooseModeController.class;

        for (int i = 1; i <= GameplayConstants.MODES_NUMBER; i++) {
            Field ellipseField = chooseModeControllerClass.getDeclaredField(ImagesShortcutConstants.BACKGROUND_STRING + i);
            Ellipse ellipse = ((Ellipse) ellipseField.get(this));

            if(i == id){
                ellipse.setStyle(StylesConstants.RED_COLOUR);
                ellipse.toFront();
            } else {
                ellipse.setStyle(null);
            }
        }
    }
}
