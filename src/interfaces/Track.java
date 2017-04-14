package interfaces;

import gameEngine.RunTrack;
import javafx.scene.layout.AnchorPane;

public interface Track {

    void createBackground(AnchorPane root);

    RunTrack getRunTrack();
}
