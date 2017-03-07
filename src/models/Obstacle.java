package models;

import dataHandler.Constants;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstacle extends Sprite {

    protected boolean isDrunk;
    private List<Obstacle> obstacles;

    public Obstacle() {
        setDestroyed(false);
        setIsDrunk(false);
        obstacles = new ArrayList<>();
    }

    private void setDestroyed(boolean destroyed) {
        this.setImage(Constants.FLAME_PATH_SMALL);
        this.setVelocity(0, 0);
        isDestroyed = destroyed;
    }

    private boolean getIsDrunk() {
        return this.isDrunk;
    }

    private void setIsDrunk(boolean b) {
        this.isDrunk = b;
    }

    private boolean isDestroyed() {
        return isDestroyed;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    private String getObstacleType() {
        return this.getName().substring(0, this.getName().length() - 1);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public Obstacle generateObstacle(int valueDrunkDrivers) {

        String[] obstacles = Constants.OBSTACLES_LIST_SMALL;
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();

        String sd = Constants.IMAGES_PATH + random + ".png";
        Obstacle obstacle = new Obstacle();

        if (random.contains("car")){
            obstacle = new EnemyDriver();
            if (new Random().nextInt(100)> valueDrunkDrivers){
                obstacle.setIsDrunk(true);
                obstacle.setPosition(obstacleX.nextInt(350 - 100) + 100, -166);
            } else {
                obstacle.setPosition(50 + obstacleX.nextInt(300), -166);
            }
        } else {
            obstacle.setPosition(50 + obstacleX.nextInt(300), -166);
        }
        obstacle.setImage(sd);

        obstacle.setName(random);


        return obstacle;
    }

    public void handleImpactByAmmo(){
        this.setDestroyed(true);
        this.setIsDrunk(false);
        this.removeWind();
    }

    public void handleImpactByCarPlayer(double velocity){
        this.setDestroyed(true);
        this.setIsDrunk(false);
        this.removeWind();
        
        if (this.getObstacleType().contains("player_car")){
            this.setVelocity(0, velocity);
        }

    }

    public void manageObstacles(GraphicsContext gc, Collectible collectible, Player player, List<Obstacle> obstacles, double velocity ) {
        for (Obstacle obstacle : obstacles) {
            String obstacleType = obstacle.getObstacleType();
            if (obstacleType.contains("player_car") && !obstacle.isDestroyed()) {

                obstacle.setVelocity(0, velocity / 3);
                if (obstacle.getIsDrunk()) {
                    if (new Random().nextInt(100)> 90) {
                        if (new Random().nextInt(2) == 0) {
                            obstacle.setTurnLeft(true);
                            obstacle.setTurnRight(false);
                        } else {
                            obstacle.setTurnRight(true);
                            obstacle.setTurnLeft(false);
                        }
                    }
                }

            } else {
                obstacle.setVelocity(0, velocity);
            }

            obstacle.update();
            obstacle.render(gc);

            if (obstacle.getBoundary().intersects(player.getBoundary())) {
                if (collectible.isImmortal()) {
                    player.addPoints(Constants.BONUS_POINTS_HIT_WITH_SHIELD*collectible.getBonusCoefficient());
                } else if (!obstacle.isDestroyed()) {
                    player.setHealthPoints(player.getHealthPoints() - Constants.OBSTACLE_DAMAGE);
                }
                obstacle.handleImpactByCarPlayer(velocity);// Comment if you want flames to go around :) .
            }
        }
    }

}
