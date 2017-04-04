package models;

import constants.CarConstants;
import constants.GeneralConstants;
import interfaces.Playable;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Collectible extends SpriteImpl {

    private int bonusCoefficient;
    private Playable player;
    private ArrayList<Collectible> collectibles;
    private boolean isImmortal;
    private boolean isDoublePtsOn;
    private double immortalityTimer;
    private double doublePtsTimer;
    private String name;

    public Collectible() {}

    public Collectible(Playable player) {
        this.bonusCoefficient = 1;
        this.player = player;
        this.collectibles = new ArrayList<>();
        this.isImmortal = false;
        this.isDoublePtsOn = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCollectible(Collectible collectible) {
        collectibles.add(collectible);
    }

    public static Collectible generateCollectible(int minLeftSide, int maxRightSide) {

        String[] collectibles= CarConstants.COLLECTIBLE_LIST_SMALL;
        String random=collectibles[new Random().nextInt(collectibles.length)];

        Random collectibleX = new Random();
        String stringDirectory = CarConstants.COLLECTIBLE_PATH + random + GeneralConstants.IMAGES_ENDING;

        Collectible collectible = new Collectible();
        collectible.setName(random);
        collectible.updateImage(stringDirectory);
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
                        this.player.addPoints(CarConstants.FUEL_TANK_BONUS * this.bonusCoefficient);
                        Notification.showPopupMessage(CarConstants.FUEL_BOTTLE_STRING, CarConstants.FUEL_NOTIFICATION_MESSAGE, currentStage);
                        //not very okay with static but cant think of sthing else write now.
                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.FUEL_BOTTLE_STRING;

                    case CarConstants.HEALTH_STRING:
                        this.player.addPoints(CarConstants.HEALTH_PACK_BONUS_POINTS * this.bonusCoefficient);
                        if (this.player.getHealthPoints() < CarConstants.HEALTH_BAR_MAX) {
                            this.player.updateHealthPoints(Math.min(player.getHealthPoints() + CarConstants.HEALTH_BONUS, CarConstants.HEALTH_BAR_MAX));
                        }
                        Notification.showPopupMessage(CarConstants.HEALTH_STRING, CarConstants.HEALTH_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.HEALTH_STRING;

                    case CarConstants.DOUBLE_POINTS_STRING:
                        player.addPoints(CarConstants.DOUBLE_BONUS_POINTS * this.bonusCoefficient);

                        if (!this.isDoublePtsOn) {
                            this.startDoublePtsTimer();
                        }
                        Notification.showPopupMessage(CarConstants.DOUBLE_POINTS_STRING, CarConstants.DOUBLE_PTS_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.DOUBLE_POINTS_STRING;

                    case CarConstants.IMMORTALITY_STRING:
                        player.addPoints(CarConstants.IMMORTALITY_BONUS * this.bonusCoefficient);
                        if (!this.isImmortal) {
                            player.addPoints( CarConstants.ARMAGEDDONS_BONUS * this.bonusCoefficient);
                            startImmortalityTimer();
                        }
                        Notification.showPopupMessage(CarConstants.IMMORTALITY_STRING, CarConstants.IMMORTALITY_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.DOUBLE_POINTS_STRING;


                    case CarConstants.ARMAGEDDON_STRING:
                        this.player.addPoints( CarConstants.ARMAGEDDONS_BONUS * this.bonusCoefficient);
                        Notification.showPopupMessage(CarConstants.ARMAGEDDON_STRING, CarConstants.ARMAGEDDONS_NOTIFICATION_MESSAGE, currentStage);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.ARMAGEDDON_STRING;

                    case CarConstants.AMMO_STRING:
                        this.player.addPoints( CarConstants.AMMO_BONUS * this.bonusCoefficient);
                        Notification.showPopupMessage(CarConstants.AMMO_STRING, CarConstants.AMMO_NOTIFICATION_MESSAGE, currentStage);
                        this.player.updateAmmunition(this.player.getAmmunition()+1);

                        collectible.setPosition(CarConstants.DESTROY_OBJECT_COORDINATES, CarConstants.DESTROY_OBJECT_COORDINATES);
                        return CarConstants.AMMO_STRING;
                }

            }
        }
        return null;
    }

    public final int getBonusCoefficient() {
        return this.bonusCoefficient;
    }

    public void updateStatus() {
        if (this.isImmortal) {
            this.updateImmortalityStatus();
        }
        if (this.isDoublePtsOn) {
            this.updateDoublePtsStatus();
        }
    }

    public final boolean isImmortal() {
        return isImmortal;
    }

    public List<Collectible> getCollectibles() {
        return this.collectibles;
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
        this.isDoublePtsOn = true;
        this.bonusCoefficient = 2;
        this.setDoublePtsTimer(CarConstants.DOUBLE_PTS_DURATION / CarConstants.FRAMES_PER_SECOND);
    }

    private void updateDoublePtsStatus() {
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
