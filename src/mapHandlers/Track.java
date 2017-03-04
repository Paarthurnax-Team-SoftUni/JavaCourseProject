package mapHandlers;

import GameEngine.RunTrack;
import dataHandler.Constants;
import dataHandler.PlayerData;

public abstract class Track {
    private RunTrack runTrack;

    public abstract void createBackground();

    public final RunTrack getRunTrack() {
        if(runTrack == null) {
            this.runTrack = new RunTrack(PlayerData.getInstance().getCurrentPlayer(), Constants.START_GAME_VELOCITY);
        }
        return runTrack;
    }
}
