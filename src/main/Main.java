package main;

import DataHandler.PlayerData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static AnchorPane windowPane;
    public static Scene theScene;
    public static Stage primStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize layout.
        primStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../views/main.fxml"));
        windowPane = (AnchorPane) root.lookup("#loginPage");
        theScene = new Scene(root, 800, 600);

        /** Setting stage to not be resizeable for the moment.
         *
         */
        primaryStage.setMaxHeight(640);
        primaryStage.setMaxWidth(800);
        primaryStage.setMinHeight(640);
        primaryStage.setMinWidth(800);
        primaryStage.setScene(theScene);
        primaryStage.setTitle("Race Game");
        primaryStage.show();

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
