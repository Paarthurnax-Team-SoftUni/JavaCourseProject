package sample;

import DataHandler.PlayerData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Insert Game Title");
        primaryStage.setScene(new Scene(root, 500, 680));
        primaryStage.show();
    }
    @Override
    public void stop() {
//        PlayerData.getInstance().storePlayersData();
    }

    @Override
    public void init() throws Exception {
        PlayerData.getInstance().loadPlayersData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
