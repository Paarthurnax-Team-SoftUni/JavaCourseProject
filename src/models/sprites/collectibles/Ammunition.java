package models.sprites.collectibles;

import models.RandomProvider;

public class Ammunition extends Collectible{

    private static final String AMMO_NAME = "ammunitions";
    private static final String AMMO_NOTIFICATION_MESSAGE = "Ammunition! You can generateAmmo once";
    private static final int AMMO_BONUS = 500;

    public Ammunition(RandomProvider randomProvider) {
        super(randomProvider);
        this.setProps();
    }

    private void setProps(){
        this.setName(AMMO_NAME);
        this.setNotificationMessage(AMMO_NOTIFICATION_MESSAGE);
        this.setBonusPoints(AMMO_BONUS);
    }
}
