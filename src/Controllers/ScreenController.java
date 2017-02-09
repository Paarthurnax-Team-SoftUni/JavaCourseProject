package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class ScreenController{
    public static Stage primaryStage;
    public static Stage startStage;
    public static Stage chooseCarStage;
    public static AnchorPane root;
    public static Pane startPage;
    public static Pane chooseCar;

    public static void setPrimaryStage(Stage primaryStage) throws IOException {
        primaryStage.setTitle(Main.TITLE);
        root = FXMLLoader.load(Main.class.getResource("../views/login.fxml"));
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.setMaxHeight(640);
        primaryStage.setMaxWidth(800);
        primaryStage.setMinHeight(640);
        primaryStage.setMinWidth(800);
        primaryStage.centerOnScreen();
        ScreenController.primaryStage = primaryStage;
    }

//    public static <T> T loadPrimaryStage(String path) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
//
//        root = FXMLLoader.load(Main.class.getResource(path));
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
//        return fxmlLoader.getController();
//    }

    public static <T> T loadStage(Stage prevStage, Stage currentStage, String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));

        if (currentStage == null) {
            currentStage = new Stage();
        }

        root = FXMLLoader.load(Main.class.getResource(path));
        currentStage.setScene(new Scene(root));
        currentStage.setTitle(Main.TITLE);
        currentStage.setResizable(false);
        closeStage(prevStage);
        System.out.println(prevStage + " closed");
//        closeStage(thirdStage);

        currentStage.show();
        System.out.println(currentStage + " shown");

        return fxmlLoader.getController();
    }
//    public static <T> T loadThirdStage(String path) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
//
//        if (thirdStage == null) {
//            thirdStage = new Stage();
//        }
//
//        chooseCar = FXMLLoader.load(Main.class.getResource(path));
//        thirdStage.setScene(new Scene(chooseCar));
//        thirdStage.setTitle(Main.TITLE);
//        thirdStage.setResizable(false);
//        closeStage(primaryStage);
//        closeStage(secondaryStage);
//
//
//        thirdStage.show();
//        return fxmlLoader.getController();
//    }

    public static void showLogin() throws IOException {
        loadStage(null, primaryStage, "../views/login.fxml");
    }

    public static void closeStage(Stage curStage){
        if (curStage != null){
            curStage.close();
        }
    }
}
