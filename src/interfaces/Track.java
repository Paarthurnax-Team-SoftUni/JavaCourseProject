package interfaces;

import gameEngine.RunTrack;
import javafx.scene.layout.AnchorPane;
import mapHandlers.TrackMode;

public interface Track {

    void createBackground(AnchorPane root);

    RunTrack getRunTrack();

    void updateMode(TrackMode trackMode);
}
