package models;

import dataHandler.Constants;
import models.interfaces.ObstacleInterface;

import java.util.Random;

public class Obstacle extends Sprite implements ObstacleInterface {
    private boolean isDestroyed;
    private boolean isDrunk;


    public Obstacle() {
        setDestroyed(false);
        setIsDrunk(false);
    }

    public boolean getIsDrunk() {
        return this.isDrunk;
    }

    public void setIsDrunk(boolean b) {
        this.isDrunk = b;
    }

    public static Obstacle generateObstacle() {

        String[] obstacles = Constants.OBSTACLES_LIST_SMALL;
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();

        String sd = Constants.IMAGES_PATH + random + ".png";
        Obstacle testObstacle = new Obstacle();
        testObstacle.setImage(sd);

        testObstacle.setName(random);
        testObstacle.setPosition(50 + obstacleX.nextInt(300), -166);

        if (random.contains("car")){
            if (new Random().nextInt(100)> 60){
                testObstacle.setIsDrunk(true);
            }
        }

        return testObstacle;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.setImage(Constants.FLAME_PATH_SMALL);
        this.setVelocity(0, 0);
        isDestroyed = destroyed;
    }

    public String getObstacleType() {
        return this.getName().substring(0, this.getName().length() - 1);
    }

}
