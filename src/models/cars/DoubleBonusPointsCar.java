package models.cars;

import annotations.Alias;
import models.sprites.PlayerCar;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;

@Alias(CarConstants.FOURTH_CAR)
public class DoubleBonusPointsCar extends PlayerCar {

    public DoubleBonusPointsCar() {
        super(GameplayConstants.INITIAL_BONUS_COEFFICIENT,
                GameplayConstants.IMMORTALITY_DURATION,
                GameplayConstants.DOUBLE_PTS_DURATION * CarConstants.BONUS_MULTIPLIER,
                GameplayConstants.HEALTH_BONUS);
    }
}
