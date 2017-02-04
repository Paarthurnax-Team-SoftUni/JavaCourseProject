package sample;

import DataHandler.PlayerData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    public static Pane mainBorderPane;
    public static Parent root = null;
    public static Scene scene;

    @Override
    public void stop() {
//        PlayerData.getInstance().storePlayersData();
    }

    @Override
    public void init() throws Exception {
        PlayerData.getInstance().loadPlayersData();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize layout.
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
            mainBorderPane = (Pane) root.lookup("#mainBorderPane");
            scene = new Scene(root, 500, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Some Title");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
