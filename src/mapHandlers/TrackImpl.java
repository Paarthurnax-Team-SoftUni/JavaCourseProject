package mapHandlers;

import constants.GameplayConstants;
import dataHandler.PlayerData;
import gameEngine.RunTrack;
import interfaces.Track;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class TrackImpl implements Track {

    private RunTrack runTrack;
    private TrackLevel trackLevel;

    public TrackImpl(TrackLevel trackLevel) {
        this.runTrack = new RunTrack(PlayerData.getInstance().getCurrentPlayer(), GameplayConstants.START_GAME_VELOCITY);
        this.trackLevel = trackLevel;
    }

    @Override
    public void createBackground(AnchorPane root) {
        this.runTrack.runGame(
                root,
                new Image(this.trackLevel.getPath()),
                this.trackLevel.getValue(),
                this.trackLevel.getMinLeftSide(),
                this.trackLevel.getMaxRightSide());
    }

    @Override
    public RunTrack getRunTrack() {
        return this.runTrack;
    }
}
