package stageHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public interface StageManager {

    FXMLLoader loadSceneToStage(Stage currentStage, String fxmlPath);

    AnchorPane getRoot();
}
