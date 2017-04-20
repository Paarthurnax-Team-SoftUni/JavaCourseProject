package models.sprites.collectibles;

import dataHandler.PlayerData;
import interfaces.Randomizer;

public class DoublePoints extends Collectible {

    private static final String DOUBLE_POINTS_NAME = "doublePoints";
    private static final String DOUBLE_POINTS_NOTIFICATION_MESSAGE = "Bonus! Double points in the next %d seconds";
    private static final int DOUBLE_POINTS_BONUS = 1000;

    public DoublePoints(Randomizer randomizer) {
        super(randomizer);
        this.setProps();
    }

    private void setProps() {
        this.updateName(DOUBLE_POINTS_NAME);
        this.setNotificationMessage(String.format(DOUBLE_POINTS_NOTIFICATION_MESSAGE, PlayerData.getInstance().getCurrentPlayer().getCar().getDoublePointsBonus()));
        this.setBonusPoints(DOUBLE_POINTS_BONUS);
    }
}
