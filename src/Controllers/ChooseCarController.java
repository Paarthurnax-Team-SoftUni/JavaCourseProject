package Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;

import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class ChooseCarController{
    public static String carId;

    @FXML
    private AnchorPane chooseCarPage;
    @FXML
    private Button returnBtn;
    @FXML
    private GridPane cars;
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
    private ImageView car1;
    @FXML
    private ImageView car2;
    @FXML
    private ImageView car3;
    @FXML
    private ImageView car4;
    @FXML
    private ImageView car5;
    @FXML
    private ImageView car6;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;


    public void initialize(){
//        changeBackgroundOnHoverUsingEvents(car1, backgroundBox1);
//        changeBackgroundOnHoverUsingEvents(car2, backgroundBox2);
//        changeBackgroundOnHoverUsingEvents(car3, backgroundBox3);
//        changeBackgroundOnHoverUsingEvents(car4, backgroundBox4);
//        changeBackgroundOnHoverUsingEvents(car5, backgroundBox5);
//        changeBackgroundOnHoverUsingEvents(car6, backgroundBox6);
    }

    public void renderStartMenu(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) returnBtn.getScene().getWindow();
        loadStage(currentStage, startStage, "../views/start.fxml");
    }

    public void chooseCar(MouseEvent ev) {
        //initialise();
        Node source = (Node) ev.getSource();
        if (source.getId().substring(0, 3).equals("car")) {
            carId = source.getId();
            backgroundFill(source.getId().substring(source.getId().length() - 1));
        }
        else if(source.getId().substring(0,5).equals("label")){
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


    public void changeBackgroundOnHoverUsingEvents(final Node node, Ellipse backgroundBox) {
        node.setStyle(null);
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                backgroundBox.setStyle("-fx-fill: rgba(255,0,0, 0.6);");
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                backgroundBox.setStyle(null);
            }
        });
    }
}
