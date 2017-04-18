package mapHandlers;

import dataHandler.CurrentHealth;
import dataHandler.CurrentStats;
import interfaces.Track;
import models.Cheat;
import models.sprites.Ammo;
import models.sprites.Obstacle;
import models.sprites.collectibles.Collectible;

import java.io.IOException;

public class TrackHandler {


    public Track getLevel(int level, CurrentHealth currentHealth, CurrentStats currentStats, Ammo ammo,
                          Collectible collectible, Obstacle obstacle, Cheat cheat) throws IOException {
        TrackLevel trackLevel = TrackLevel.values()[level - 1];
        return new TrackImpl(trackLevel, currentHealth,
                currentStats, ammo, collectible, obstacle, cheat);
    }
}
