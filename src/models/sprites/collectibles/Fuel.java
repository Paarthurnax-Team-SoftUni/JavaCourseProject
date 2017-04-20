package models.sprites.collectibles;

import annotations.Collectable;
import interfaces.Randomizer;


@Collectable
public class Fuel extends Collectible {

    private static final String FUEL_NAME = "fuel";
    private static final String FUEL_NOTIFICATION_MESSAGE = "Extra fuel! +5 seconds";
    private static final int FUEL_BONUS = 250;

    public Fuel(Randomizer randomizer) {
        super(randomizer);
        this.setProps();
    }

    private void setProps() {
        this.updateName(FUEL_NAME);
        this.setNotificationMessage(FUEL_NOTIFICATION_MESSAGE);
        this.setBonusPoints(FUEL_BONUS);
    }
}
