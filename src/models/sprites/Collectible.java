package models.sprites;

import constants.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import models.Notification;
import models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Collectible extends Sprite {

    private int bonusCoefficient;
    private Player player;
    private ArrayList<Collectible> collectibles;
    private boolean isImmortal;
    private boolean isDoublePtsOn;
    private double immortalityTimer;
    private double doublePtsTimer;

    public Collectible() {
    }

    public Collectible(Player player) {
        this.bonusCoefficient = 1;
        this.player = player;
        this.collectibles = new ArrayList<>();
    }

    public static Collectible generateCollectible(int minLeftSide, int maxRightSide) {

        String[] collectibles = CollctiblesAndObstaclesConstants.COLLECTIBLE_LIST_SMALL;
        String random = collectibles[new Random().nextInt(collectibles.length)];

        Random collectibleX = new Random();
        String stringDirectory = CollctiblesAndObstaclesConstants.COLLECTIBLE_PATH + random + ".png";

        Collectible collectible = new Collectible();
        collectible.setName(random);
        collectible.setImage(stringDirectory);
        collectible.setPosition(collectibleX.nextInt(maxRightSide - minLeftSide) + minLeftSide, -166);

        return collectible;
    }

    public final int getBonusCoefficient() {
        return this.bonusCoefficient;
    }

    public void updateStatus() {
        if (isImmortal) {
            this.updateImmortalityStatus();
        }
        if (isDoublePtsOn) {
            this.updateDoublePointsStatus();
        }
    }

    public final boolean isImmortal() {
        return this.isImmortal;
    }

    public List<Collectible> getCollectibles() {
        return this.collectibles;
    }

    public void addCollectible(Collectible collectible) {
        this.collectibles.add(collectible);
    }

    public String visualizeCollectible(GraphicsContext gc, double velocity, Stage currentStage) {
        for (Collectible collectible : this.collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.update();
            collectible.render(gc);

            if (collectible.intersects(this.player.getCar())) {
                switch (collectible.getCollectibleType()) {
                    //fuel bonus
                    case CollctiblesAndObstaclesConstants.FUEL_BOTTLE_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.FUEL_TANK_BONUS,
                                CollctiblesAndObstaclesConstants.FUEL_BOTTLE_STRING,
                                NotificationsConstants.FUEL_NOTIFICATION_MESSAGE);
                        return CollctiblesAndObstaclesConstants.FUEL_BOTTLE_STRING;

                    //health bonus
                    case CollctiblesAndObstaclesConstants.HEALTH_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.HEALTH_PACK_BONUS_POINTS,
                                CollctiblesAndObstaclesConstants.HEALTH_STRING,
                                NotificationsConstants.HEALTH_NOTIFICATION_MESSAGE);
                        if (this.player.getHealthPoints() < GameplayConstants.HEALTH_BAR_MAX) {
                            this.player.setHealthPoints(Math.min(this.player.getHealthPoints() + GameplayConstants.HEALTH_BONUS, GameplayConstants.HEALTH_BAR_MAX));
                        }
                        return CollctiblesAndObstaclesConstants.HEALTH_STRING;

                    //double points bonus
                    case CollctiblesAndObstaclesConstants.DOUBLE_POINTS_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.DOUBLE_BONUS_POINTS,
                                CollctiblesAndObstaclesConstants.DOUBLE_POINTS_STRING,
                                NotificationsConstants.DOUBLE_PTS_NOTIFICATION_MESSAGE);
                        if (!isDoublePtsOn) {
                            startDoublePointsTimer();
                        }
                        return CollctiblesAndObstaclesConstants.DOUBLE_POINTS_STRING;

                    //immortality bonus
                    case CollctiblesAndObstaclesConstants.IMMORTALITY_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.IMMORTALITY_BONUS,
                                CollctiblesAndObstaclesConstants.IMMORTALITY_STRING,
                                NotificationsConstants.IMMORTALITY_NOTIFICATION_MESSAGE);
                        if (!isImmortal) {
                            player.addPoints(GameplayConstants.ARMAGEDDONS_BONUS * bonusCoefficient);
                            startImmortalityTimer();
                        }
                        return CollctiblesAndObstaclesConstants.DOUBLE_POINTS_STRING;

                    //armagedon bonus
                    case CollctiblesAndObstaclesConstants.ARMAGEDDON_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.ARMAGEDDONS_BONUS,
                                CollctiblesAndObstaclesConstants.ARMAGEDDON_STRING,
                                NotificationsConstants.ARMAGEDDONS_NOTIFICATION_MESSAGE);
                        return CollctiblesAndObstaclesConstants.ARMAGEDDON_STRING;

                    //bullet bonus
                    case CollctiblesAndObstaclesConstants.AMMO_STRING:
                        processCollectible(collectible, currentStage,
                                GameplayConstants.AMMO_BONUS,
                                CollctiblesAndObstaclesConstants.AMMO_STRING,
                                NotificationsConstants.AMMO_NOTIFICATION_MESSAGE);
                        player.getCar().setAmmunition(player.getCar().getAmmunition() + 1);
                        return CollctiblesAndObstaclesConstants.AMMO_STRING;
                }
            }
        }
        return null;
    }

    private void processCollectible(Collectible collectible, Stage currentStage, int
            bonusPoints, String bonusName, String message) {
        this.player.addPoints(bonusPoints * this.bonusCoefficient);
        Notification.showPopupMessage(bonusName, message, currentStage);
        collectible.setPosition(GameplayConstants.DESTROY_OBJECT_COORDINATES, GameplayConstants.DESTROY_OBJECT_COORDINATES);
    }

    private String getCollectibleType() {
        int index = this.getName().charAt(11) - '0';

        switch (index) {
            case 1:
                return CollctiblesAndObstaclesConstants.FUEL_BOTTLE_STRING;
            case 2:
                return CollctiblesAndObstaclesConstants.HEALTH_STRING;
            case 3:
                return CollctiblesAndObstaclesConstants.DOUBLE_POINTS_STRING;
            case 4:
                return CollctiblesAndObstaclesConstants.IMMORTALITY_STRING;
            case 5:
                return CollctiblesAndObstaclesConstants.ARMAGEDDON_STRING;
            case 6:
                return CollctiblesAndObstaclesConstants.AMMO_STRING;
        }
        return CollctiblesAndObstaclesConstants.BONUS_POINTS_STRING;
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
