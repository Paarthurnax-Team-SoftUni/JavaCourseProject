package models.sprites.collectibles;

import dataHandler.ModelsParamsManager;
import dataHandler.PlayerData;
import interfaces.Randomizer;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import models.Player;
import models.sprites.PlayerCar;
import models.sprites.Sprite;
import utils.constants.*;
import utils.notifications.Notification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Collectible extends Sprite {

    private String notificationMessage;
    private int bonusPoints;
    private int bonusCoefficient;
    private boolean isDoublePtsOn;
    private double immortalityTimer;
    private double doublePtsTimer;
    private Player player;
    private List<Collectible> collectibles;
    private Randomizer randomizer;
    private PlayerCar playerCar;

    public Collectible(Randomizer randomizer) {
        this.bonusCoefficient = GameplayConstants.INITIAL_BONUS_COEFFICIENT;
        this.player = PlayerData.getInstance().getCurrentPlayer();
        this.playerCar = player.getCar();
        this.collectibles = new ArrayList<>();
        this.randomizer = randomizer;
    }

    public void add(Collectible collectible) {
        this.collectibles.add(collectible);
    }

    public Iterable<Collectible> getCollectibles() {
        return this.collectibles;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public void updateStatus() {
        if (this.player.getCar().isImmortal()) {
            this.updateImmortalityStatus();
        }
        if (this.isDoublePtsOn) {
            this.updateDoublePointsStatus();
        }
    }

    public Collectible generateCollectible(int minLeftSide, int maxRightSide) throws IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        String[] collectibles = ModelsParamsManager.getInstance().getCollectablesArray();

        String className = collectibles[this.randomizer.next(collectibles.length)];
        Class collectibleClass = Class.forName(ResourcesConstants.COLLECTIBLES_PACKAGE + className);
        Constructor<Collectible> constructor = collectibleClass.getDeclaredConstructor(Randomizer.class);
        Collectible collectible = constructor.newInstance(this.randomizer);

        collectible.updateName(className.toLowerCase());
        collectible.updateImage(CollectiblesAndObstaclesConstants.COLLECTIBLE_PATH + className.toLowerCase() + ImagesShortcutConstants
                .PNG_FILE_EXTENSION);
        collectible.updatePosition(this.randomizer.next(maxRightSide - minLeftSide) + minLeftSide, GameplayConstants
                .OBSTACLE_ANIMATION_Y_OFFSET);

        return collectible;
    }

    public String visualizeCollectible(GraphicsContext gc, double velocity) {
        Stage currentStage = (Stage) gc.getCanvas().getScene().getWindow();
        for (Collectible collectible : this.collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.update();
            collectible.render(gc);

            if (collectible.intersects(this.player.getCar())) {
                this.player.addPoints(collectible.bonusPoints * collectible.bonusCoefficient);
                Notification.showPopupMessage(collectible.getName(), collectible.notificationMessage, currentStage);
                collectible.updatePosition(GameplayConstants.DESTROY_OBJECT_COORDINATES, GameplayConstants
                        .DESTROY_OBJECT_COORDINATES);

                takeBonus(collectible.getName());
                return collectible.getName();
            }
        }
        return null;
    }

    private void takeBonus(String name) {
        switch (name) {
            case CollectiblesAndObstaclesConstants.HEALTH_STRING:
                if (this.player.getHealthPoints() < GameplayConstants.HEALTH_BAR_MAX) {
                    this.player.updateHealthPoints(Math.min(this.player.getHealthPoints() + this.playerCar.getHealthBonus(),
                            GameplayConstants.HEALTH_BAR_MAX));
                }
                break;
            case CollectiblesAndObstaclesConstants.POINTS_STRING:
                if (!isDoublePtsOn) {
                    startDoublePointsTimer();
                }
                break;
            case CollectiblesAndObstaclesConstants.IMMORTALITY_STRING:
                if (!this.player.getCar().isImmortal()) {
                    player.addPoints(GameplayConstants.ARMAGEDDONS_BONUS * bonusCoefficient);
                    startImmortalityTimer();
                }
                break;
            case CollectiblesAndObstaclesConstants.AMMO_STRING:
                player.getCar().updateAmmunition(player.getCar().getAmmunition() + this.playerCar.getBulletsBonus());
                break;
        }
    }

    private void startImmortalityTimer() {
        this.player.getCar().updateImmortal(true);
        this.immortalityTimer = this.playerCar.getImmortalityBonus() / GeneralConstants.FRAMES_PER_SECOND;
    }

    private void updateImmortalityStatus() {
        this.immortalityTimer--;
        if (this.immortalityTimer < 0) {
            this.player.getCar().updateImmortal(false);
        }
    }

    public void clearObstacles() {
        this.collectibles.clear();
    }

    private void startDoublePointsTimer() {
        this.isDoublePtsOn = true;
        this.bonusCoefficient = CollectiblesAndObstaclesConstants.DOUBLE_POINTS_BONUS_COEFFICIENT;
        this.doublePtsTimer = this.playerCar.getDoublePointsBonus() / GeneralConstants.FRAMES_PER_SECOND;
    }

    private void updateDoublePointsStatus() {
        this.doublePtsTimer--;
        if (this.doublePtsTimer < 0) {
            this.isDoublePtsOn = false;
            this.bonusCoefficient = CollectiblesAndObstaclesConstants.DOUBLE_POINTS_INITIAL_VALUE;
        }
    }
}