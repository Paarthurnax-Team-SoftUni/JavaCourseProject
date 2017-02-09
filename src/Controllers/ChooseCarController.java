package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static Controllers.ScreenController.chooseCarStage;
import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class ChooseCarController{
    public static String carId;

    @FXML
    public AnchorPane chooseCarPage;
    @FXML
    public Button returnBtn;
    @FXML
    public GridPane cars;


    public void renderStartMenu(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) returnBtn.getScene().getWindow();
        loadStage(currentStage, startStage, "../views/start.fxml");
    }

    public void chooseCar(MouseEvent ev) {
        Node source = (Node)ev.getSource();
        carId = source.getId();
    }
}
