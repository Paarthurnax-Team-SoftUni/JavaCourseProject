package controllers;

import dataHandler.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class ScreenController{
    private static volatile ScreenController instance = null;
    private Stage primaryStage;
    private Stage startStage;
    private Stage chooseCarStage;
    private Stage gamePlayStage;
    private Stage gameWinStage;
    private Stage gameOverStage;
    private AnchorPane root;

    public static ScreenController getInstance() {
        if(instance == null) {
            synchronized (ScreenController.class) {
                if(instance == null) {
                    instance = new ScreenController();
                }
            }
        }
        return instance;
    }

    public Stage getStartStage() {
        return startStage;
    }

    public Stage getChooseCarStage() {
        return chooseCarStage;
    }

    public Stage getGamePlayStage() {
        return gamePlayStage;
    }

    public Stage getGameOverStage() {
        return gameOverStage;
    }

    public AnchorPane getRoot() {
        return root;
    }

    private void setRoot(AnchorPane root) {
        this.root = root;
    }

    public void setPrimaryStage(Stage primaryStage) throws IOException {
        primaryStage.setTitle(Constants.GAME_TITLE);
        AnchorPane anchorPane = FXMLLoader.load(Main.class.getResource(Constants.LOGIN_VIEW_PATH));
        setRoot(anchorPane);
        primaryStage.setScene(new Scene(this.root,800,600));
        primaryStage.setMaxHeight(640);
        primaryStage.setMaxWidth(800);
        primaryStage.setMinHeight(640);
        primaryStage.setMinWidth(800);
        primaryStage.centerOnScreen();
        this.primaryStage = primaryStage;
    }

    public <T> T loadStage(Stage prevStage, Stage currentStage, String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));

        if (currentStage == null) {
            currentStage = new Stage();
        }

        currentStage.getIcons().add(new Image(Constants.LOGO_PATH));

        root = FXMLLoader.load(Main.class.getResource(path));

        currentStage.setScene(new Scene(this.root));
        currentStage.setTitle(Constants.GAME_TITLE);
        currentStage.setResizable(false);
        currentStage.show();
        closeStage(prevStage);

        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, t -> {
            if (t.getCode() == KeyCode.SPACE) {
                t.consume();
            }
        });


        return fxmlLoader.getController();
    }

    public void showLogin() throws IOException {
        loadStage(null, primaryStage, Constants.LOGIN_VIEW_PATH);
    }

    private void closeStage(Stage curStage){
        if (curStage != null){
            curStage.close();
        }
    }
}
