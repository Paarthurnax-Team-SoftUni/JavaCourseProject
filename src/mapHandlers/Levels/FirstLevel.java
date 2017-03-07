package mapHandlers.Levels;


import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import mapHandlers.Track;
import mapHandlers.TrackLevel;

public class FirstLevel extends Track {

    @Override
    public void createBackground(AnchorPane root) {
        super.getRunTrack().runGame(new Image(TrackLevel.FIRST_LEVEL.getPath()), root, TrackLevel.FIRST_LEVEL.getValue(), TrackLevel.FIRST_LEVEL.getMinLeftSide(), TrackLevel.FIRST_LEVEL.getMaxRightSide());
    }
}
