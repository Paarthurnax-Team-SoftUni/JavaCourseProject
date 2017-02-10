package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
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
    @FXML
    public Ellipse backgroundBox1;
    @FXML
    public Ellipse backgroundBox2;
    @FXML
    public Ellipse backgroundBox3;
    @FXML
    public Ellipse backgroundBox4;
    @FXML
    public Ellipse backgroundBox5;
    @FXML
    public Ellipse backgroundBox6;
    public ImageView car1;


    public void renderStartMenu(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) returnBtn.getScene().getWindow();
        loadStage(currentStage, startStage, "../views/start.fxml");
    }

    public void chooseCar(MouseEvent ev) {
        Node source = (Node) ev.getSource();
        if (source.getId().substring(0, 3).equals("car")) {
            carId = source.getId();
            backgroundFill(source.getId().substring(source.getId().length() - 1));
        }
        else if(source.getId().substring(0,3).equals("bac")){
            carId = "car" + source.getId().substring(source.getId().length()-1);
            backgroundFill(source.getId().substring(source.getId().length()-1));
        }
        returnBtn.setVisible(true);
    }

    private void backgroundFill(String id){
        backgroundBox1.setStyle(null);
        backgroundBox2.setStyle(null);
        backgroundBox3.setStyle(null);
        backgroundBox4.setStyle(null);
        backgroundBox5.setStyle(null);
        backgroundBox6.setStyle(null);

        switch (id) {
            case "1":
                backgroundBox1.setStyle("-fx-fill: rgba(255,0,0, 0.6);");
                break;
            case "2":
                backgroundBox2.setStyle("-fx-fill: rgba(255,0,0, 0.6);");
                break;
            case "3":
                backgroundBox3.setStyle("-fx-fill: rgba(255,0,0, 0.6);");
                break;
            case "4":
                backgroundBox4.setStyle("-fx-fill: rgba(255,0,0, 0.6);");
                break;
            case "5":
                backgroundBox5.setStyle("-fx-fill: rgba(255,0,0, 0.6);");
                break;
            case "6":
                backgroundBox6.setStyle("-fx-fill: rgba(255,0,0, 0.6);");
                break;
        }
    }
}
