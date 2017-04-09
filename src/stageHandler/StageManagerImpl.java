package stageHandler;

import constants.GeneralConstants;
import constants.ResourcesConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManagerImpl implements StageManager {

    private AnchorPane root;

    public StageManagerImpl() {
        this.setRoot(root);
    }

    @Override
    public AnchorPane getRoot() {
        return this.root;
    }

    private void setRoot(AnchorPane root) {
        this.root = root;
    }

    public FXMLLoader loadSceneToStage(Stage currentStage, String fxmlPath) {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));

        try {
            Pane root = fxmlLoader.load();
            Scene scene = new Scene(root, GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);

            currentStage.setTitle(GeneralConstants.GAME_TITLE);
            currentStage.setResizable(false);
            currentStage.centerOnScreen();
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setRoot(fxmlLoader.getRoot());
        currentStage.show();
        currentStage.getIcons().add(new Image(ResourcesConstants.LOGO_PATH));

        return fxmlLoader;
    }
}
