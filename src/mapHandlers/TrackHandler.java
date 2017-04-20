package mapHandlers;

import dataHandler.CurrentHealth;
import dataHandler.CurrentStats;
import dataHandler.PlayerData;
import interfaces.Track;
import models.Cheat;
import models.sprites.Obstacles.Obstacle;
import models.sprites.Weapon;
import models.sprites.collectibles.Collectible;

import java.io.IOException;

public class TrackHandler {
    private TrackMode trackMode;

    public TrackHandler() {
        this.trackMode = PlayerData.getInstance().getCurrentPlayer().getCurrentTrackmode();
    }

    public Track getLevel(int level, CurrentHealth currentHealth, CurrentStats currentStats, Weapon weapon,
                          Collectible collectible, Obstacle obstacle, Cheat cheat) throws IOException {
        TrackLevel trackLevel = TrackLevel.values()[level - 1];
        return new TrackImpl(trackLevel, currentHealth,
                currentStats, weapon, collectible, obstacle, cheat, this.trackMode);
    }
}
