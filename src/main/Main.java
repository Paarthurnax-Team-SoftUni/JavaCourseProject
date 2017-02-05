package main;

import DataHandler.PlayerData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    public static AnchorPane windowPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize layout.
            Parent root = FXMLLoader.load(getClass().getResource("../views/main.fxml"));
            windowPane = (AnchorPane) root.lookup("#loginPage");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.setTitle("Race Game");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void init() throws Exception {
        PlayerData.getInstance().loadPlayersData();
    }

    @Override
    public void stop() {
//        PlayerData.getInstance().storePlayersData();
    }
}
