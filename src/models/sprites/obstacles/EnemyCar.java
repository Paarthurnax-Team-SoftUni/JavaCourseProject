package models.sprites.obstacles;

import interfaces.Randomizer;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;
import utils.constants.GeneralConstants;


public class EnemyCar extends Obstacle {

    public EnemyCar(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public void addVelocity(double x, double y) {
        if (x < 0) {
            if (super.getPositionX() > CarConstants.X_AXIS_LEFT_BOUND) {
                super.updateVelocityX(x);
            }
            if (super.getPositionX() < CarConstants.X_AXIS_LEFT_BOUND_BUFFER) {
                super.setTurningLeft(false);
            }
        } else if (x > 0) {
            if (super.getPositionX() < CarConstants.X_AXIS_RIGHT_BOUND) {
                super.updateVelocityX(x);
            }
            if (super.getPositionX() > CarConstants.X_AXIS_RIGHT_BOUND_BUFFER) {
                super.setTurningRight(false);
            }
        }

        if (y < 0 && super.getPositionY() > GameplayConstants.CANVAS_Y_END) {
            super.updateVelocityY(y);
        } else if (y > 0 && super.getPositionY() < GeneralConstants.CANVAS_HEIGHT - super.getImageHeight() * GameplayConstants.IMAGE_HEIGHT_OFFSET) {
            super.updateVelocityY(y);
        }
    }
}
