package models.sprites.Obstacles;

import javafx.scene.canvas.GraphicsContext;
import models.Player;
import interfaces.Randomizer;
import models.sprites.RotatableSprite;
import utils.constants.*;

import java.util.ArrayList;
import java.util.List;

public class Obstacle extends RotatableSprite {

    private boolean isDrunk;
    private boolean isDestroyed;
    private List<Obstacle> obstacles;
    private Randomizer randomizer;

    public Obstacle(Randomizer randomizer) {
        this.obstacles = new ArrayList<>();
        this.randomizer = randomizer;
    }

    public void add(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    public Iterable<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    private void setDestroyed(boolean isDestroyed) {
        this.updateImage(ResourcesConstants.FLAME_PATH_SMALL);
        this.setVelocity(0, 0);
        this.isDestroyed = isDestroyed;
    }

    public Obstacle generateObstacle(int drunkDrivers, int minLeftSide, int maxRightSide) {

        String[] obstacles = CollectiblesAndObstaclesConstants.OBSTACLES_LIST_SMALL;
        String random = (obstacles[this.randomizer.next(obstacles.length)]);
        String image = ResourcesConstants.IMAGES_PATH + random + ".png";

        Obstacle obstacle = new Obstacle(this.randomizer);

        if (random.contains(CarConstants.CAR_STRING) && this.randomizer.next(100) > drunkDrivers) {
            obstacle = new EnemyCar(this.randomizer);
            obstacle.setIsDrunk(true);
            obstacle.updatePosition(this.randomizer.next((maxRightSide - GameplayConstants.OBSTACLES_BOUNDS) -
                    (minLeftSide + GameplayConstants.OBSTACLES_BOUNDS)) + minLeftSide + GameplayConstants
                    .OBSTACLES_BOUNDS, GameplayConstants.OBSTACLE_ANIMATION_Y_OFFSET);
        }

        obstacle.updatePosition(this.randomizer.next(maxRightSide - minLeftSide) + minLeftSide, GameplayConstants
                .OBSTACLE_ANIMATION_Y_OFFSET);
        obstacle.updateImage(image);
        obstacle.updateName(random);

        return obstacle;
    }

    public void visualizeObstacle(GraphicsContext gc, double velocity, Player player) {
        for (Obstacle obstacle : this.obstacles) {
            String obstacleType = obstacle.getObstacleType();
            if (obstacleType.contains(ImagesShortcutConstants.PLAYER_CAR) && !obstacle.isDestroyed()) {
                obstacle.setVelocity(0, velocity / 3);

                if (obstacle.isDrunk && this.randomizer.next(100) > 90) {
                    if (this.randomizer.next(2) == 0) {
                        obstacle.setTurningLeft(true);
                        obstacle.setTurningRight(false);
                    } else {
                        obstacle.setTurningRight(true);
                        obstacle.setTurningLeft(false);
                    }
                }
            } else {
                obstacle.setVelocity(0, velocity);
            }

            obstacle.update();
            obstacle.render(gc);

            if (obstacle.intersects(player.getCar())) {
                if (player.getCar().isImmortal()) {
                    player.addPoints(GameplayConstants.BONUS_POINTS_HIT_WITH_SHIELD);
                } else if (!obstacle.isDestroyed()) {
                    player.updateHealthPoints(player.getHealthPoints() - GameplayConstants.OBSTACLE_DAMAGE);
                }
                obstacle.handleImpactByCarPlayer(velocity);
            }
        }
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

        if (this.getObstacleType().contains(ImagesShortcutConstants.PLAYER_CAR)) {
            this.setVelocity(0, velocity);
        }
    }

    public void clearObstacles() {
        this.obstacles.clear();
    }

    public void setIsDrunk(boolean isDrunk) {
        this.isDrunk = isDrunk;
    }

    private String getObstacleType() {
        return this.getName().substring(0, this.getName().length() - 1);
    }
}
