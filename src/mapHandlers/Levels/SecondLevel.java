package mapHandlers.Levels;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import mapHandlers.Track;
import mapHandlers.TrackLevel;

public class SecondLevel extends Track{

    @Override
    public void createBackground(AnchorPane root) {
        super.getRunTrack().runGame(new Image(TrackLevel.SECOND_LEVEL.getPath()), root, TrackLevel.SECOND_LEVEL.getValue());
    }
}
