package mapHandlers.levels;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import mapHandlers.Track;

public class SecondLevel extends Track {

    @Override
    public void createBackground(AnchorPane root) {
        super.getRunTrack().runGame(
                root,
                new Image(TrackLevel.SECOND_LEVEL.getPath()),
                TrackLevel.SECOND_LEVEL.getValue(),
                TrackLevel.SECOND_LEVEL.getMinLeftSide(),
                TrackLevel.SECOND_LEVEL.getMaxRightSide());
    }
}
