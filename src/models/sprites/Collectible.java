package models.sprites;

import constants.CarConstants;
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

        String[] collectibles = CarConstants.COLLECTIBLE_LIST_SMALL;
        String random = collectibles[new Random().nextInt(collectibles.length)];

        Random collectibleX = new Random();
        String stringDirectory = CarConstants.COLLECTIBLE_PATH + random + ".png";

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
                    case CarConstants.FUEL_BOTTLE_STRING:
                        processCollectible(collectible, currentStage,
                                CarConstants.FUEL_TANK_BONUS,
                                CarConstants.FUEL_BOTTLE_STRING,
                                CarConstants.FUEL_NOTIFICATION_MESSAGE);
                        return CarConstants.FUEL_BOTTLE_STRING;

                    //health bonus
                    case CarConstants.HEALTH_STRING:
                        processCollectible(collectible, currentStage,
                                CarConstants.HEALTH_PACK_BONUS_POINTS,
                                CarConstants.HEALTH_STRING,
                                CarConstants.HEALTH_NOTIFICATION_MESSAGE);
                        if (this.player.getHealthPoints() < CarConstants.HEALTH_BAR_MAX) {
                            this.player.setHealthPoints(Math.min(this.player.getHealthPoints() + CarConstants.HEALTH_BONUS, CarConstants.HEALTH_BAR_MAX));
                        }
                        return CarConstants.HEALTH_STRING;

                    //double points bonus
                    case CarConstants.DOUBLE_POINTS_STRING:
                        processCollectible(collectible, currentStage,
                                CarConstants.DOUBLE_BONUS_POINTS,
                                CarConstants.DOUBLE_POINTS_STRING,
                                CarConstants.DOUBLE_PTS_NOTIFICATION_MESSAGE);
                        if (!isDoublePtsOn) {
                            startDoublePointsTimer();
                        }
                        return CarConstants.DOUBLE_POINTS_STRING;

                    //immortality bonus
                    case CarConstants.IMMORTALITY_STRING:
                        processCollectible(collectible, currentStage,
                                CarConstants.IMMORTALITY_BONUS,
                                CarConstants.IMMORTALITY_STRING,
                                CarConstants.IMMORTALITY_NOTIFICATION_MESSAGE);
                        if (!isImmortal) {
                            player.addPoints(CarConstants.ARMAGEDDONS_BONUS * bonusCoefficient);
                            startImmortalityTimer();
                        }
                        return CarConstants.DOUBLE_POINTS_STRING;

                    //armagedon bonus
                    case CarConstants.ARMAGEDDON_STRING:
                        processCollectible(collectible, currentStage,
                                CarConstants.ARMAGEDDONS_BONUS,
                                CarConstants.ARMAGEDDON_STRING,
                                CarConstants.ARMAGEDDONS_NOTIFICATION_MESSAGE);
                        return CarConstants.ARMAGEDDON_STRING;

                    //bullet bonus
                    case CarConstants.AMMO_STRING:
                        processCollectible(collectible, currentStage,
                                CarConstants.AMMO_BONUS,
                                CarConstants.AMMO_STRING,
                                CarConstants.AMMO_NOTIFICATION_MESSAGE);
                        player.getCar().setAmmunition(player.getCar().getAmmunition() + 1);
                        return CarConstants.AMMO_STRING;
                }
            }
        }
        return null;
    }

    private void processCollectible(Collectible collectible, Stage currentStage, int
            bonusPoints, String bonusName, String message) {
        this.player.addPoints(bonusPoints * this.bonusCoefficient);
        Notification.showPopupMessage(bonusName, message, currentStage);
        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
    }

    private String getCollectibleType() {
        int index = this.getName().charAt(11) - '0';

        switch (index) {
            case 1:
                return CarConstants.FUEL_BOTTLE_STRING;
            case 2:
                return CarConstants.HEALTH_STRING;
            case 3:
                return CarConstants.DOUBLE_POINTS_STRING;
            case 4:
                return CarConstants.IMMORTALITY_STRING;
            case 5:
                return CarConstants.ARMAGEDDON_STRING;
            case 6:
                return CarConstants.AMMO_STRING;
        }
        return CarConstants.BONUS_POINTS_STRING;
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
        this.setDoublePtsTimer(CarConstants.DOUBLE_PTS_DURATION / CarConstants.FRAMES_PER_SECOND);
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
        this.setImmortalityTimer(CarConstants.IMMORTALITY_DURATION / CarConstants.FRAMES_PER_SECOND);
    }

    private void updateImmortalityStatus() {
        this.setImmortalityTimer(this.getImmortalityTimer() - 1);
        if (this.getImmortalityTimer() < 0) {
            this.isImmortal = false;
        }
    }
}
