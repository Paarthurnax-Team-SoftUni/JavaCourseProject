package models.sprites.collectibles;

import models.RandomProvider;

public class Fuel extends Collectible {

    private static final String FUEL_NAME = "fuelBottle";
    private static final String FUEL_NOTIFICATION_MESSAGE = "Extra fuel! +5 seconds";
    private static final int FUEL_BONUS = 250;

    public Fuel(RandomProvider randomProvider) {
        super(randomProvider);
        this.setProps();
    }

    private void setProps() {
        this.setName(FUEL_NAME);
        this.setNotificationMessage(FUEL_NOTIFICATION_MESSAGE);
        this.setBonusPoints(FUEL_BONUS);
    }
}
