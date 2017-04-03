package models;

import constants.CarConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

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

    public Collectible() {}

    public Collectible(Player player) {
        this.bonusCoefficient = 1;
        this.player = player;
        this.collectibles = new ArrayList<>();
        this.isImmortal = false;
        this.isDoublePtsOn = false;
    }

    private String getCollectibleType(){
        switch (this.getName()){
            case CarConstants.COLLECTIBLE + 1 + CarConstants.HALF_SIZE_NAME:
                return CarConstants.FUEL_BOTTLE_STRING;
            case CarConstants.COLLECTIBLE + 2 + CarConstants.HALF_SIZE_NAME:
                return CarConstants.HEALTH_STRING;
            case CarConstants.COLLECTIBLE + 3 + CarConstants.HALF_SIZE_NAME:
                return CarConstants.DOUBLE_POINTS_STRING;
            case CarConstants.COLLECTIBLE + 4 + CarConstants.HALF_SIZE_NAME:
                return CarConstants.IMMORTALITY_STRING;
            case CarConstants.COLLECTIBLE + 5 + CarConstants.HALF_SIZE_NAME:
                return CarConstants.ARMAGEDDON_STRING;
            case CarConstants.COLLECTIBLE + 6 + CarConstants.HALF_SIZE_NAME:
                return CarConstants.AMMO_STRING;
        }
        return CarConstants.BONUS_POINTS_STRING;
    }

    private double getImmortalityTimer() {
        return immortalityTimer;
    }

    private void setImmortalityTimer(double immortalityTimer) {
        this.immortalityTimer = immortalityTimer;
    }

    private double getDoublePtsTimer() {
        return doublePtsTimer;
    }

    private void setDoublePtsTimer(double doublePtsTimer) {
        this.doublePtsTimer = doublePtsTimer;
    }

    private void startDoublePtsTimer() {
        isDoublePtsOn = true;
        bonusCoefficient = 2;
        this.setDoublePtsTimer(CarConstants.DOUBLE_PTS_DURATION / CarConstants.FRAMES_PER_SECOND);
    }

    private void updateDoublePtsStatus() {
        this.setDoublePtsTimer(this.getDoublePtsTimer() - 1);
        if (this.getDoublePtsTimer() < 0) {
            isDoublePtsOn = false;
            bonusCoefficient = 1;
        }
    }

    private void startImmortalityTimer() {
        isImmortal = true;
        this.setImmortalityTimer(CarConstants.IMMORTALITY_DURATION / CarConstants.FRAMES_PER_SECOND);
    }

    private void updateImmortalityStatus() {
        this.setImmortalityTimer(this.getImmortalityTimer() - 1);
        if (this.getImmortalityTimer() < 0) {
            isImmortal = false;
        }
    }

    public void addCollectible(Collectible collectible) {
        collectibles.add(collectible);
    }

    public static Collectible generateCollectible(int minLeftSide, int maxRightSide) {

        String[] collectibles= CarConstants.COLLECTIBLE_LIST_SMALL;
        String random=collectibles[new Random().nextInt(collectibles.length)];

        Random collectibleX = new Random();
        String stringDirectory = CarConstants.COLLECTIBLE_PATH + random + ".png";

        Collectible collectible = new Collectible();
        collectible.setName(random);
        collectible.setImage(stringDirectory);
        collectible.setPosition(collectibleX.nextInt(maxRightSide - minLeftSide) + minLeftSide, -166);

        return collectible;
    }

    public String visualizeCollectible(GraphicsContext gc, double velocity, Stage currentStage) {
        for (Collectible collectible : collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.update();
            collectible.render(gc);

            if (collectible.getBoundary().intersects(player.getBoundary())) {
                switch (collectible.getCollectibleType()) {

                    case CarConstants.FUEL_BOTTLE_STRING:
                        player.addPoints(CarConstants.FUEL_TANK_BONUS*bonusCoefficient);
                        Notification.showPopupMessage(CarConstants.FUEL_BOTTLE_STRING, CarConstants.FUEL_NOTIFICATION_MESSAGE, currentStage);
                        //not very okay with static but cant think of sthing else write now.
                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.FUEL_BOTTLE_STRING;

                    case CarConstants.HEALTH_STRING:
                        player.addPoints(CarConstants.HEALTH_PACK_BONUS_POINTS*bonusCoefficient);
                        if (player.getHealthPoints() < CarConstants.HEALTH_BAR_MAX) {
                            player.setHealthPoints(Math.min(player.getHealthPoints() + CarConstants.HEALTH_BONUS, CarConstants.HEALTH_BAR_MAX));
                        }
                        Notification.showPopupMessage(CarConstants.HEALTH_STRING, CarConstants.HEALTH_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.HEALTH_STRING;

                    case CarConstants.DOUBLE_POINTS_STRING:
                        player.setPoints(player.getPoints() + CarConstants.DOUBLE_BONUS_POINTS*bonusCoefficient);
                        if (!isDoublePtsOn) {
                            startDoublePtsTimer();
                        }
                        Notification.showPopupMessage(CarConstants.DOUBLE_POINTS_STRING, CarConstants.DOUBLE_PTS_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.DOUBLE_POINTS_STRING;

                    case CarConstants.IMMORTALITY_STRING:
                        player.addPoints(CarConstants.IMMORTALITY_BONUS*bonusCoefficient);
                        if (!isImmortal) {
                            player.addPoints( CarConstants.ARMAGEDDONS_BONUS*bonusCoefficient);
                            startImmortalityTimer();
                        }
                        Notification.showPopupMessage(CarConstants.IMMORTALITY_STRING, CarConstants.IMMORTALITY_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.DOUBLE_POINTS_STRING;


                    case CarConstants.ARMAGEDDON_STRING:
                        player.addPoints( CarConstants.ARMAGEDDONS_BONUS*bonusCoefficient);
                        Notification.showPopupMessage(CarConstants.ARMAGEDDON_STRING, CarConstants.ARMAGEDDONS_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.ARMAGEDDON_STRING;

                    case CarConstants.AMMO_STRING:
                        player.addPoints( CarConstants.AMMO_BONUS*bonusCoefficient);
                        Notification.showPopupMessage(CarConstants.AMMO_STRING, CarConstants.AMMO_NOTIFICATION_MESSAGE, currentStage);
                        player.setAmmunition(player.getAmmunition()+1);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.AMMO_STRING;
                }

            }
        }
        return null;
    }

    public final int getBonusCoefficient() {
        return bonusCoefficient;
    }

    public void updateStatus() {
        if (isImmortal) {
            this.updateImmortalityStatus();
        }
        if (isDoublePtsOn) {
            this.updateDoublePtsStatus();
        }
    }

    public final boolean isImmortal() {
        return isImmortal;
    }

    public List<Collectible> getCollectibles() {
        return this.collectibles;
    }
}
