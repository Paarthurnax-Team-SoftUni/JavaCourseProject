package models.sprites;

import constants.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import models.Notification;
import models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Collectible extends CollectibleSprite {

    private Player player;
    private ArrayList<Collectible> collectibles;
    private int bonusCoefficient;
    private boolean isImmortal;
    private boolean isDoublePtsOn;
    private double immortalityTimer;
    private double doublePtsTimer;

    public Collectible() {
    }

    public Collectible(Player player) {
        this.bonusCoefficient = GameplayConstants.INITIAL_BULLETS_COUNTS;
        this.player = player;
        this.collectibles = new ArrayList<>();
    }

    public void addCollectible(Collectible collectible) {
        this.collectibles.add(collectible);
    }

    public List<Collectible> getCollectibles() {
        return this.collectibles;
    }

    public final int getBonusCoefficient() {
        return this.bonusCoefficient;
    }

    public void updateStatus() {
        if (this.isImmortal) {
            this.updateImmortalityStatus();
        }
        if (this.isDoublePtsOn) {
            this.updateDoublePointsStatus();
        }
    }

    public final boolean isImmortal() {
        return this.isImmortal;
    }

    public Collectible generateCollectible(int minLeftSide, int maxRightSide) {

        String[] collectibles = CollectiblesAndObstaclesConstants.COLLECTIBLE_LIST_SMALL;
        String random = collectibles[new Random().nextInt(collectibles.length)];

        Random collectibleX = new Random();
        String stringDirectory = CollectiblesAndObstaclesConstants.COLLECTIBLE_PATH + random + ImagesShortcutConstants.PNG_FILE_EXTENSION;

        Collectible collectible = new Collectible();
        collectible.updateName(random);
        collectible.setImage(stringDirectory);
        collectible.updatePosition(collectibleX.nextInt(maxRightSide - minLeftSide) + minLeftSide, GameplayConstants.OBSTACLE_ANIMATION_Y_OFFSET);

        return collectible;
    }

    public String visualizeCollectible(GraphicsContext gc, double velocity) {
        Stage currentStage = (Stage) gc.getCanvas().getScene().getWindow();
        for (Collectible collectible : this.collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.update();
            collectible.render(gc);

            if (collectible.intersects(this.player.getCar())) {
                switch (collectible.getCollectibleType()) {
                    //fuel bonus
                    case CollectiblesAndObstaclesConstants.FUEL_BOTTLE_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.FUEL_TANK_BONUS,
                                CollectiblesAndObstaclesConstants.FUEL_BOTTLE_STRING,
                                NotificationsConstants.FUEL_NOTIFICATION_MESSAGE);
                        return CollectiblesAndObstaclesConstants.FUEL_BOTTLE_STRING;

                    //health bonus
                    case CollectiblesAndObstaclesConstants.HEALTH_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.HEALTH_PACK_BONUS_POINTS,
                                CollectiblesAndObstaclesConstants.HEALTH_STRING,
                                NotificationsConstants.HEALTH_NOTIFICATION_MESSAGE);
                        if (this.player.getHealthPoints() < GameplayConstants.HEALTH_BAR_MAX) {
                            this.player.updateHealthPoints(Math.min(this.player.getHealthPoints() + GameplayConstants.HEALTH_BONUS, GameplayConstants.HEALTH_BAR_MAX));
                        }
                        return CollectiblesAndObstaclesConstants.HEALTH_STRING;

                    //double points bonus
                    case CollectiblesAndObstaclesConstants.DOUBLE_POINTS_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.DOUBLE_BONUS_POINTS,
                                CollectiblesAndObstaclesConstants.DOUBLE_POINTS_STRING,
                                NotificationsConstants.DOUBLE_PTS_NOTIFICATION_MESSAGE);
                        if (!isDoublePtsOn) {
                            startDoublePointsTimer();
                        }
                        return CollectiblesAndObstaclesConstants.DOUBLE_POINTS_STRING;

                    //immortality bonus
                    case CollectiblesAndObstaclesConstants.IMMORTALITY_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.IMMORTALITY_BONUS,
                                CollectiblesAndObstaclesConstants.IMMORTALITY_STRING,
                                NotificationsConstants.IMMORTALITY_NOTIFICATION_MESSAGE);
                        if (!isImmortal) {
                            player.addPoints(GameplayConstants.ARMAGEDDONS_BONUS * bonusCoefficient);
                            startImmortalityTimer();
                        }
                        return CollectiblesAndObstaclesConstants.DOUBLE_POINTS_STRING;

                    //armagedon bonus
                    case CollectiblesAndObstaclesConstants.ARMAGEDDON_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.ARMAGEDDONS_BONUS,
                                CollectiblesAndObstaclesConstants.ARMAGEDDON_STRING,
                                NotificationsConstants.ARMAGEDDONS_NOTIFICATION_MESSAGE);
                        return CollectiblesAndObstaclesConstants.ARMAGEDDON_STRING;

                    //bullet bonus
                    case CollectiblesAndObstaclesConstants.AMMO_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.AMMO_BONUS,
                                CollectiblesAndObstaclesConstants.AMMO_STRING,
                                NotificationsConstants.AMMO_NOTIFICATION_MESSAGE);
                        player.getCar().setAmmunition(player.getCar().getAmmunition() + 1);
                        return CollectiblesAndObstaclesConstants.AMMO_STRING;
                }
            }
        }
        return null;
    }

    private void processCollectible(Collectible collectible, Stage currentStage, int
            bonusPoints, String bonusName, String message) {
        this.player.addPoints(bonusPoints * this.bonusCoefficient);
        Notification.showPopupMessage(bonusName, message, currentStage);
        collectible.updatePosition(GameplayConstants.DESTROY_OBJECT_COORDINATES, GameplayConstants.DESTROY_OBJECT_COORDINATES);
    }

    private String getCollectibleType() {
        int index = this.getName().charAt(11) - '0';

        switch (index) {
            case 1:
                return CollectiblesAndObstaclesConstants.FUEL_BOTTLE_STRING;
            case 2:
                return CollectiblesAndObstaclesConstants.HEALTH_STRING;
            case 3:
                return CollectiblesAndObstaclesConstants.DOUBLE_POINTS_STRING;
            case 4:
                return CollectiblesAndObstaclesConstants.IMMORTALITY_STRING;
            case 5:
                return CollectiblesAndObstaclesConstants.ARMAGEDDON_STRING;
            case 6:
                return CollectiblesAndObstaclesConstants.AMMO_STRING;
        }
        return CollectiblesAndObstaclesConstants.BONUS_POINTS_STRING;
    }

    private double getImmortalityTimer() {
        return this.immortalityTimer;
    }

    private void setImmortalityTimer(double immortalityTimer) {
        this.immortalityTimer = immortalityTimer;
    }

    private double getDoublePtsTimer() {
        return this.doublePtsTimer;
    }

    private void setDoublePtsTimer(double doublePtsTimer) {
        this.doublePtsTimer = doublePtsTimer;
    }

    private void startDoublePointsTimer() {
        this.isDoublePtsOn = true;
        this.bonusCoefficient = 2;
        this.setDoublePtsTimer(GameplayConstants.DOUBLE_PTS_DURATION / GeneralConstants.FRAMES_PER_SECOND);
    }

    private void updateDoublePointsStatus() {
        this.setDoublePtsTimer(this.getDoublePtsTimer() - 1);
        if (this.getDoublePtsTimer() < 0) {
            this.isDoublePtsOn = false;
            this.bonusCoefficient = 1;
        }
    }

    private void startImmortalityTimer() {
        this.isImmortal = true;
        this.setImmortalityTimer(GameplayConstants.IMMORTALITY_DURATION / GeneralConstants.FRAMES_PER_SECOND);
    }

    private void updateImmortalityStatus() {
        this.setImmortalityTimer(this.getImmortalityTimer() - 1);
        if (this.getImmortalityTimer() < 0) {
            this.isImmortal = false;
        }
    }
}