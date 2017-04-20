package models.sprites.collectibles;

import annotations.Collectable;
import interfaces.Randomizer;
@Collectable
public class Ammunition extends Collectible {

    private static final String AMMO_NAME = "ammunition";
    private static final String AMMO_NOTIFICATION_MESSAGE = "Ammunition! You can cause mayhem!";
    private static final int AMMO_BONUS = 500;

    public Ammunition(Randomizer randomizer) {
        super(randomizer);
        this.setProps();
    }

    private void setProps() {
        this.updateName(AMMO_NAME);
        this.setNotificationMessage(AMMO_NOTIFICATION_MESSAGE);
        this.setBonusPoints(AMMO_BONUS);
    }
}
