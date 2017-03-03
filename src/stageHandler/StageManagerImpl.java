package stageHandler;

import dataHandler.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManagerImpl implements StageManager {

    public FXMLLoader loadSceneToStage(Stage currentStage, String fxmlPath, Modality modality) {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));

        try {
            Pane root = fxmlLoader.load();
            Scene scene = new Scene(root, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);

            currentStage.setTitle(Constants.GAME_TITLE);
            currentStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        currentStage.centerOnScreen();
    }
}
