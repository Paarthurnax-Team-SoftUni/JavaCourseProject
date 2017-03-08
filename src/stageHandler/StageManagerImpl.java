package stageHandler;

import utils.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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

    public FXMLLoader loadSceneToStage(Stage currentStage, String fxmlPath, Modality modality) {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));

        try {
            Pane root = fxmlLoader.load();
            Scene scene = new Scene(root, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);

            currentStage.setTitle(Constants.GAME_TITLE);
            currentStage.setResizable(false);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setRoot(fxmlLoader.getRoot());
        this.initModality(currentStage, modality);
        return fxmlLoader;
    }

    private void initModality(Stage currentStage, Modality modality) {
        if (modality == null) {
            modality = Modality.NONE;
        }
        switch (modality) {
            case NONE:
                currentStage.show();
                break;
            case APPLICATION_MODAL:
                currentStage.initModality(Modality.APPLICATION_MODAL);
                currentStage.showAndWait();
                break;
            case WINDOW_MODAL:
                currentStage.initModality(Modality.WINDOW_MODAL);
                currentStage.showAndWait();
                break;
        }
        currentStage.getIcons().add(new Image(Constants.LOGO_PATH));
        currentStage.centerOnScreen();
    }
}
