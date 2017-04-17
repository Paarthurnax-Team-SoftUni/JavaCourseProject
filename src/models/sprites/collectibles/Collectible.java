package models.sprites.collectibles;

import dataHandler.PlayerData;
import models.sprites.CollectibleSprite;
import utils.constants.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import utils.notifications.Notification;
import models.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public abstract class Collectible extends CollectibleSprite {

    private String notificationMessage;
    private int bonusPoints;
    private int bonusCoefficient;
    private boolean isDoublePtsOn;
    private double immortalityTimer;
    private double doublePtsTimer;
    private Player player;

    public Collectible() {
        this.bonusCoefficient = GameplayConstants.INITIAL_BONUS_COEFFICIENT;
        this.player = PlayerData.getInstance().getCurrentPlayer();
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

    public String visualizeCollectible(GraphicsContext gc, double velocity) {
        Stage currentStage = (Stage) gc.getCanvas().getScene().getWindow();
        this.setVelocity(0, velocity);
        this.update();
        this.render(gc);

        if (this.intersects(this.player.getCar())) {
            this.player.addPoints(this.bonusPoints * this.bonusCoefficient);
            Notification.showPopupMessage(this.getName(), this.notificationMessage, currentStage);
            this.updatePosition(GameplayConstants.DESTROY_OBJECT_COORDINATES, GameplayConstants
                    .DESTROY_OBJECT_COORDINATES);

            takeBonus(this.getName());
            return this.getName();
        }

        return null;
    }

    private void takeBonus(String name) {
        switch (name){
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
                player.getCar().setAmmunition(player.getCar().getAmmunition() + 1);
                break;
        }
    }

    private void startImmortalityTimer() {
        this.player.getCar().setImmortal(true);
        this.immortalityTimer = GameplayConstants.IMMORTALITY_DURATION / GeneralConstants.FRAMES_PER_SECOND;
    }

    private void updateImmortalityStatus() {
        this.immortalityTimer--;
        if (this.immortalityTimer < 0) {
            this.player.getCar().setImmortal(false);
        }
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