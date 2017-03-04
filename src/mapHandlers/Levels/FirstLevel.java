package mapHandlers.Levels;


import dataHandler.Constants;
import javafx.scene.image.Image;
import mapHandlers.Track;

public class FirstLevel extends Track {

    @Override
    public void createBackground() {
        super.getRunTrack().runGame(new Image(Constants.TRACK_BACKGROUND_PATH_TEST));
    }
}
