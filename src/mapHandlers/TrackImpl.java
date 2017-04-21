package mapHandlers;

import dataHandler.CurrentHealth;
import dataHandler.CurrentStats;
import dataHandler.PlayerData;
import gameEngine.RunTrack;
import interfaces.Track;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import models.Cheat;
import models.sprites.obstacles.Obstacle;
import models.sprites.Weapon;
import models.sprites.collectibles.Collectible;
import utils.constants.GameplayConstants;


public class TrackImpl implements Track {

    private RunTrack runTrack;
    private TrackLevel trackLevel;
    private TrackMode trackMode;

    public TrackImpl(TrackLevel trackLevel, CurrentHealth currentHealth, CurrentStats currentStats, Weapon weapon,
                     Collectible collectible, Obstacle obstacle, Cheat cheat, TrackMode trackMode) {
        this.trackMode = trackMode;
        this.runTrack = new RunTrack(PlayerData.getInstance().getCurrentPlayer(),
                GameplayConstants.START_GAME_VELOCITY, this.trackMode, currentHealth,
                currentStats, weapon, collectible, obstacle, cheat);
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

    @Override
    public void updateMode(TrackMode trackMode) {
        this.trackMode = trackMode;
    }
}
