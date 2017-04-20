package models.cars;

import annotations.Alias;
import models.sprites.PlayerCar;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;

@Alias(CarConstants.THIRD_CAR)
public class DoubleImmortalityBonusCar extends PlayerCar {

    public DoubleImmortalityBonusCar() {
        super(GameplayConstants.INITIAL_BONUS_COEFFICIENT
                , GameplayConstants.IMMORTALITY_DURATION,
                GameplayConstants.DOUBLE_PTS_DURATION,
                GameplayConstants.HEALTH_BONUS);
    }
}
