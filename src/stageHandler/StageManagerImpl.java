package stageHandler;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManagerImpl implements StageManager {

    public AnchorPane root;

    public StageManagerImpl() {
        this.setRoot(root);
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public FXMLLoader loadSceneToStage(Stage currentStage, String fxmlPath) {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));

        try {
            Pane root = fxmlLoader.load();
            Scene scene = new Scene(root, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);

            currentStage.setTitle(Constants.GAME_TITLE);
            currentStage.setResizable(false);
            currentStage.centerOnScreen();
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setRoot(fxmlLoader.getRoot());
        currentStage.show();
        currentStage.getIcons().add(new Image(Constants.LOGO_PATH));

        return fxmlLoader;
    }
}
