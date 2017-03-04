package models;

import dataHandler.Constants;

import java.util.Random;

public class Obstacle extends Sprite{
    private boolean isDestroyed;

    public Obstacle() {
        setDestroyed(false);
    }

    public  static Obstacle generateObstacle(){

        String[] obstacles = Constants.OBSTACLES_LIST;
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();

        String sd = Constants.IMAGES_PATH + random + ".png";
        Obstacle testObstacle = new Obstacle();
        testObstacle.setImage(sd);

        testObstacle.setName(random);
        testObstacle.setPosition(50 + obstacleX.nextInt(300), -166);

        return testObstacle;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.setImage(Constants.FLAME_PATH);
        this.setVelocity(0, 0);
        isDestroyed = destroyed;
    }

    public String getObstacleType(){
        return this.getName().substring(0, this.getName().length()-1);
    }

}
