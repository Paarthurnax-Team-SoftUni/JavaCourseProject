package models.sprites;

import constants.CollectiblesAndObstaclesConstants;
import constants.GameplayConstants;
import constants.ResourcesConstants;
import javafx.scene.canvas.GraphicsContext;
import models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstacle extends DestroyableSprite {

    private boolean isDrunk;
    private List<Obstacle> obstacles;

    public Obstacle() {
        this.obstacles = new ArrayList<>();
    }

    public void addObstacle(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public Obstacle generateObstacle(int drunkDrivers, int minLeftSide, int maxRightSide) {

        String[] obstacles = CollectiblesAndObstaclesConstants.OBSTACLES_LIST_SMALL;
        String random = (obstacles[new Random().nextInt(obstacles.length)]);
        String image = ResourcesConstants.IMAGES_PATH + random + ".png";

        Random obstacleX = new Random();
        Obstacle obstacle = new Obstacle();

        if (random.contains("car")) {
            obstacle = new EnemyCar();

            if (new Random().nextInt(100) > drunkDrivers) {
                obstacle.setIsDrunk(true);
                obstacle.setPosition(obstacleX.nextInt((maxRightSide - 50) - (minLeftSide + 50)) + minLeftSide + 50, -166);
            } else {
                obstacle.setPosition(obstacleX.nextInt(maxRightSide - minLeftSide) + minLeftSide, -166);
            }

        } else {
            obstacle.setPosition(obstacleX.nextInt(maxRightSide - minLeftSide) + minLeftSide, -166);
        }

        obstacle.setImage(image);
        obstacle.setName(random);

        return obstacle;
    }

    public void handleImpactByAmmo(Player player) {
        player.addPoints(GameplayConstants.DESTROYED_OBJECT_BONUS);
        this.setDestroyed(true);
        this.setIsDrunk(false);
        this.removeWind();
    }

    public void handleImpactByCarPlayer(double velocity) {
        this.setDestroyed(true);
        this.setIsDrunk(false);
        this.removeWind();

        if (this.getObstacleType().contains("player_car")) {
            this.setVelocity(0, velocity);
        }
    }

    public void manageObstacles(GraphicsContext gc, Collectible collectible, Player player, List<Obstacle> obstacles, double velocity) {
        for (Obstacle obstacle : obstacles) {
            String obstacleType = obstacle.getObstacleType();

            if (obstacleType.contains("player_car") && !obstacle.isDestroyed()) {

                obstacle.setVelocity(0, velocity / 3);

                if (obstacle.getIsDrunk() && new Random().nextInt(100) > 90) {
                    if (new Random().nextInt(2) == 0) {
                        obstacle.setTurnLeft(true);
                        obstacle.setTurnRight(false);
                    } else {
                        obstacle.setTurnRight(true);
                        obstacle.setTurnLeft(false);
                    }
                }
            } else {
                obstacle.setVelocity(0, velocity);
            }

            obstacle.update();
            obstacle.render(gc);

            if (obstacle.intersects(player.getCar())) {
                if (collectible.isImmortal()) {
                    player.addPoints(GameplayConstants.BONUS_POINTS_HIT_WITH_SHIELD * collectible.getBonusCoefficient());
                } else if (!obstacle.isDestroyed()) {
                    player.setHealthPoints(player.getHealthPoints() - GameplayConstants.OBSTACLE_DAMAGE);
                }
                obstacle.handleImpactByCarPlayer(velocity);// Comment if you want flames to go around :) .
            }
        }
    }

    @Override
    public void setDestroyed(boolean isDestroyed) {
        this.setImage(ResourcesConstants.FLAME_PATH_SMALL);
        this.setVelocity(0, 0);
        super.setDestroyed(isDestroyed);
    }

    private boolean getIsDrunk() {
        return this.isDrunk;
    }

    private void setIsDrunk(boolean isDrunk) {
        this.isDrunk = isDrunk;
    }

    private String getObstacleType() {
        return this.getName().substring(0, this.getName().length() - 1);
    }
}
