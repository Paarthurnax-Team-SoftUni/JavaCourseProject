package models.sprites.collectibles;

import dataHandler.PlayerData;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import models.Player;
import models.RandomProvider;
import models.sprites.CollectibleSprite;
import utils.constants.CollectiblesAndObstaclesConstants;
import utils.constants.GameplayConstants;
import utils.constants.GeneralConstants;
import utils.constants.ImagesShortcutConstants;
import utils.notifications.Notification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Collectible extends CollectibleSprite {

    private String notificationMessage;
    private int bonusPoints;
    private int bonusCoefficient;
    private boolean isDoublePtsOn;
    private double immortalityTimer;
    private double doublePtsTimer;
    private Player player;
    private List<Collectible> collectibles;
    private RandomProvider randomProvider;

    public Collectible(RandomProvider randomProvider) {
        this.bonusCoefficient = GameplayConstants.INITIAL_BONUS_COEFFICIENT;
        this.player = PlayerData.getInstance().getCurrentPlayer();
        this.collectibles = new ArrayList<>();
        this.randomProvider = randomProvider;
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
        String[] collectibles = CollectiblesAndObstaclesConstants.COLLECTIBLE_LIST_SMALL;
        String random = collectibles[this.randomProvider.next(collectibles.length)];

        String className = random.toUpperCase().charAt(0) + random.substring(1, random.length());
        Class collectibleClass = Class.forName("models.sprites.collectibles." + className);
        Constructor<Collectible> constructor = collectibleClass.getDeclaredConstructor(RandomProvider.class);
        Collectible collectible = constructor.newInstance(this.randomProvider);

        collectible.updateName(random);
        collectible.setImage(CollectiblesAndObstaclesConstants.COLLECTIBLE_PATH + random + ImagesShortcutConstants
                .PNG_FILE_EXTENSION);
        collectible.updatePosition(this.randomProvider.next(maxRightSide - minLeftSide) + minLeftSide, GameplayConstants
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
            case "health":
                if (this.player.getHealthPoints() < GameplayConstants.HEALTH_BAR_MAX) {
                    this.player.updateHealthPoints(Math.min(this.player.getHealthPoints() + GameplayConstants.HEALTH_BONUS,
                            GameplayConstants.HEALTH_BAR_MAX));
                }
                break;
            case "doublePoints":
                if (!isDoublePtsOn) {
                    startDoublePointsTimer();
                }
                break;
            case "immortality":
                if (!this.player.getCar().isImmortal()) {
                    player.addPoints(GameplayConstants.ARMAGEDDONS_BONUS * bonusCoefficient);
                    startImmortalityTimer();
                }
            case "ammunition":
                player.getCar().updateAmmunition(player.getCar().getAmmunition() + 1);
                break;
        }
    }

    private void startImmortalityTimer() {
        this.player.getCar().updateImmortal(true);
        this.immortalityTimer = GameplayConstants.IMMORTALITY_DURATION / GeneralConstants.FRAMES_PER_SECOND;
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
        this.bonusCoefficient = 2;
        this.doublePtsTimer = GameplayConstants.DOUBLE_PTS_DURATION / GeneralConstants.FRAMES_PER_SECOND;
    }

    private void updateDoublePointsStatus() {
        this.doublePtsTimer--;
        if (this.doublePtsTimer < 0) {
            this.isDoublePtsOn = false;
            this.bonusCoefficient = 1;
        }
    }
}