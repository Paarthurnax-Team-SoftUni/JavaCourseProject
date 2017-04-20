package models.cars;

import annotations.Alias;
import models.sprites.PlayerCar;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;

@Alias(CarConstants.DEFAULT_CAR)
public class BasicCar extends PlayerCar {

    public BasicCar() {
        super(GameplayConstants.INITIAL_BONUS_COEFFICIENT,
                GameplayConstants.IMMORTALITY_DURATION,
                GameplayConstants.DOUBLE_PTS_DURATION,
                GameplayConstants.HEALTH_BONUS);
    }

}
