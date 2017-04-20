package models.cars;

import annotations.Alias;
import models.sprites.PlayerCar;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;

@Alias(CarConstants.SECOND_CAR)
public class DoubleAmmoBonusCar extends PlayerCar {

    public DoubleAmmoBonusCar() {
        super(GameplayConstants.INITIAL_AMMO_DROP * CarConstants.BONUS_MULTIPLIER, GameplayConstants.IMMORTALITY_DURATION, GameplayConstants.DOUBLE_PTS_DURATION, GameplayConstants.HEALTH_BONUS);
    }
}
