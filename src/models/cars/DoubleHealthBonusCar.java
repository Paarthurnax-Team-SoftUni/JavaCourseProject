package models.cars;

import annotations.Alias;
import models.sprites.PlayerCar;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;

@Alias(CarConstants.FIFTH_CAR)
public class DoubleHealthBonusCar extends PlayerCar {
    public DoubleHealthBonusCar() {
        super(2, GameplayConstants.IMMORTALITY_DURATION, GameplayConstants.DOUBLE_PTS_DURATION, GameplayConstants.HEALTH_BONUS * CarConstants.BONUS_MULTIPLIER);
    }
}
