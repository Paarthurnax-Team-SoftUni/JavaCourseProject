package models;

import utils.Constants;

public class EnemyDriver extends Obstacle {



    @Override
    public void addVelocity(double x, double y) {
        if (x < 0) {
            if (super.getPositionX() > 50) {
                super.setVelocityX(x);
            }
            if (super.getPositionX() < 100) {
                super.setTurnLeft(false);
            }
        } else if (x > 0) {
            if (super.getPositionX() < 400) {
                super.setVelocityX(x);
            }
            if (super.getPositionX() > 350) {
                super.setTurnRight(false);
            }
        }
        if (y < 0) {
            if (super.getPositionY() > 300) {
                super.setVelocityY(y);
            }
        } else if (y > 0) {
            if (super.getPositionY() < Constants.CANVAS_HEIGHT -super.getHeight() * 2) {
                super.setVelocityY(y);
            }
        }
    }


}
