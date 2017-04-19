package models.sprites.collectibles;

import interfaces.Randomizer;

public class Health extends Collectible {

    private static final String HEALTH_NAME = "health";
    private static final String HEALTH_NOTIFICATION_MESSAGE = "Health! Restore your health";
    private static final int HEALTH_BONUS = 500;

    public Health(Randomizer randomizer) {
        super(randomizer);
        this.setProps();
    }

    private void setProps() {
        this.updateName(HEALTH_NAME);
        this.setNotificationMessage(HEALTH_NOTIFICATION_MESSAGE);
        this.setBonusPoints(HEALTH_BONUS);
    }
}
