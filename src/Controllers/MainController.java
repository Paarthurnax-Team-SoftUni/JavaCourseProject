package Controllers;

import MapHandlers.Track;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Main;

import java.awt.*;
import java.io.IOException;

public class MainController {

    @FXML
    public AnchorPane loginPage;
    public static AnchorPane homePage;
    @FXML
    public Button loginBtn;
    public TextField playerName;
    public Button showScoresBtn;
    public Button closeBtn;
    public Button startBtn;
    public Button chooseCarBtn;
    public Rectangle backgroundBox;


    @FXML
    public void showHighScores() {
        showHighScoresDialog();
    }

    public static void MainController(){

    }

    @FXML
    public void chooseCar() throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(Main.class.getResource("../views/chooseCar.fxml"));
        root.lookup("#chooseCarPage");
        Stage stage = (Stage) chooseCarBtn.getScene().getWindow();
        stage.setTitle("Choose Your Car");

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        final ImageView imv = new ImageView();
        final Image image2 = new Image(Main.class.getResourceAsStream("../resources/images/player_car.png"));
        imv.setImage(image2);

        final HBox pictureRegion = new HBox();

        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 10, 10);


        root.getChildren().add(gridpane);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();


    }

    private void showHighScoresDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Best Slav Ranking");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/highScoresDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Can't load the High Scores dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    @FXML
    public void startNewGame() {

        Track.createBackground();

        showScoresBtn.setVisible(false);
        startBtn.setVisible(false);
        closeBtn.setVisible(false);
        chooseCarBtn.setVisible(false);
        backgroundBox.setVisible(false);

//        gameStarted = true;
//        setTime();
//        isGameRunning = true;
    }

    @FXML
    public void onClose() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void showStartPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/startPage.fxml"));
        AnchorPane windowPane = (AnchorPane) root.lookup("#homePage");
        Main.windowPane=windowPane;
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setTitle("Race Game");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

}
