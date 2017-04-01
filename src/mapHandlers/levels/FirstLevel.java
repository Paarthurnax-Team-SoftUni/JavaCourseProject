package mapHandlers.levels;


import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import mapHandlers.Track;

public class FirstLevel extends Track {

    @Override
    public void createBackground(AnchorPane root) {
        super.getRunTrack().runGame(
                root,
                new Image(TrackLevel.FIRST_LEVEL.getPath()),
                TrackLevel.FIRST_LEVEL.getValue(),
                TrackLevel.FIRST_LEVEL.getMinLeftSide(),
                TrackLevel.FIRST_LEVEL.getMaxRightSide());
    }
}
