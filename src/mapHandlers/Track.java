package mapHandlers;

import constants.CarConstants;
import dataHandler.PlayerData;
import gameEngine.RunTrack;
import javafx.scene.layout.AnchorPane;

public abstract class Track {

    private RunTrack runTrack;

    public abstract void createBackground(AnchorPane root);

    public final RunTrack getRunTrack() {
        if (runTrack == null) {
            this.runTrack = new RunTrack(PlayerData.getInstance().getCurrentPlayer(), CarConstants.START_GAME_VELOCITY);
        }
        return runTrack;
    }
}
