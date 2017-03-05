package models;

import dataHandler.Constants;

public class EnemyDriver extends Obstacle {



    @Override
    public void addVelocity(double x, double y) {
        if (x < 0) {
            if (positionX > 50) {
                velocityX += x;
            }
            if (positionX < 100) {
                super.setTurnLeft(false);
            }
        } else if (x > 0) {
            if (positionX < 400) {
                velocityX += x;
            }
            if (positionX > 350) {
                super.setTurnRight(false);
            }
        }
        if (y < 0) {
            if (positionY > 300) {
                velocityY += y;
            }
        } else if (y > 0) {
            if (positionY < Constants.CANVAS_HEIGHT - this.height * 2) {
                velocityY += y;
            }
        }
    }
}
