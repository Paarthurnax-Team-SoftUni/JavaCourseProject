package models.sprites.collectibles;

import dataHandler.PlayerData;
import interfaces.Randomizer;

public class Immortality extends Collectible {

    private static final String IMMORTALITY_NAME = "immortality";
    private static final String IMMORTALITY_NOTIFICATION_MESSAGE = "Immortality! You are invincible for the next %d seconds";
    private static final int IMMORTALITY_BONUS = 500;

    public Immortality(Randomizer randomizer) {
        super(randomizer);
        this.setProps();
    }

    private void setProps() {
        this.updateName(IMMORTALITY_NAME);
        this.setNotificationMessage(String.format(IMMORTALITY_NOTIFICATION_MESSAGE, PlayerData.getInstance().getCurrentPlayer().getCar().getImmortalityBonus()));
        this.setBonusPoints(IMMORTALITY_BONUS);
    }
}
