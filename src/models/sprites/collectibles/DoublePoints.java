package models.sprites.collectibles;

import models.Randomizer;

public class DoublePoints extends Collectible {

    private static final String DOUBLE_POINTS_NAME = "doublePoints";
    private static final String DOUBLE_POINTS_NOTIFICATION_MESSAGE = "Bonus! Double points in the next 5 seconds";
    private static final int DOUBLE_POINTS_BONUS = 1000;

    public DoublePoints(Randomizer randomizer) {
        super(randomizer);
        this.setProps();
    }

    private void setProps() {
        this.setName(DOUBLE_POINTS_NAME);
        this.setNotificationMessage(DOUBLE_POINTS_NOTIFICATION_MESSAGE);
        this.setBonusPoints(DOUBLE_POINTS_BONUS);
    }
}
