package mapHandlers;

import GameEngine.RunTrack;
import dataHandler.Constants;
import dataHandler.PlayerData;
import javafx.scene.layout.AnchorPane;

public abstract class Track {
    private RunTrack runTrack;

    public abstract void createBackground(AnchorPane root);

    public final RunTrack getRunTrack() {
        if(runTrack == null) {
            this.runTrack = new RunTrack(PlayerData.getInstance().getCurrentPlayer(), Constants.START_GAME_VELOCITY);
        }
        return runTrack;
    }
}
