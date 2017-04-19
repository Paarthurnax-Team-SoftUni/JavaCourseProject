package models.sprites.collectibles;

import models.RandomProvider;

public class Immortality extends Collectible {

    private static final String IMMORTALITY_NAME = "immortality";
    private static final String IMMORTALITY_NOTIFICATION_MESSAGE = "Immortality! You are invincible for the next 5 seconds";
    private static final int IMMORTALITY_BONUS = 500;

    public Immortality(RandomProvider randomProvider) {
        super(randomProvider);
        this.setProps();
    }

    private void setProps() {
        this.setName(IMMORTALITY_NAME);
        this.setNotificationMessage(IMMORTALITY_NOTIFICATION_MESSAGE);
        this.setBonusPoints(IMMORTALITY_BONUS);
    }
}
