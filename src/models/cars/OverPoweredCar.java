package models.cars;

import annotations.Alias;
import models.sprites.PlayerCar;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;

@Alias(CarConstants.SIXTH_CAR)
public class OverPoweredCar extends PlayerCar {
    public OverPoweredCar() {
        super(GameplayConstants.INITIAL_BONUS_COEFFICIENT * CarConstants.BONUS_MULTIPLIER,
                GameplayConstants.IMMORTALITY_DURATION * CarConstants.BONUS_MULTIPLIER,
                GameplayConstants.DOUBLE_PTS_DURATION * CarConstants.BONUS_MULTIPLIER,
                GameplayConstants.HEALTH_BONUS * CarConstants.BONUS_MULTIPLIER);
    }
}
