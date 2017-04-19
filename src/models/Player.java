package models;

import utils.constants.ErrorConstants;
import utils.constants.GameplayConstants;
import models.sprites.PlayerCar;

public class Player {

    private String name;
    private long highScore;
    private double money;
    private long points;
    private int healthPoints;
    private int maxLevelPassed;
    private int id;
    private PlayerCar car;

    public Player(PlayerCar playerCar) {
        this.setPoints(0L);
        this.car = playerCar;
    }

    public Player(PlayerCar playerCar, String name, long highScore, double money, int healthPoints) {
        this(playerCar);
        this.setName(name);
        this.setHighScore(highScore);
        this.setMoney(money);
        this.setHealthPoints(healthPoints);
    }

    public void updateCarId (String carId) {
        this.car.updateCarID(carId);
    }

    public String getCarId() {
        return this.car.getCarId();
    }

    public int getId() {
        return id;
    }

    public void updateId(int id) {
        this.setId(id);
    }

    private void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException(ErrorConstants.ID_EXCEPTION);
        }
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void updateName(String name) {
        this.setName(name);
    }

    private void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(ErrorConstants.NAME_EXCEPTION);
        }
        this.name = name;
    }

    public Long getHighScore() {
        return this.highScore;
    }

    public void updateHighScore(long highScore) {
        this.setHighScore(highScore);
    }

    private void setHighScore(long highScore) {
        if(highScore < 0) {
            throw new IllegalArgumentException(ErrorConstants.HIGHSCORE_ERROR);
        }
        this.highScore = highScore;
    }

    public Double getMoney() {
        return this.money;
    }

    public void updateMoney(double money) {
        this.setMoney(money);
    }

    private void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException(ErrorConstants.MONEY_EXCEPTION);
        }
        this.money = money;
    }

    public long getPoints() {
        return this.points;
    }

    public void updatePoints(long points) {
        this.setPoints(points);
    }

    private void setPoints(long points) {
        if (points < 0) {
            throw new IllegalArgumentException(ErrorConstants.POINTS_EXCEPTION);
        }
        this.points = points;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public void updateHealthPoints(int healthPoints) {
        this.setHealthPoints(healthPoints);
    }

    private void setHealthPoints(int healthPoints) {
        if (healthPoints < 0) {
            throw new IllegalArgumentException(ErrorConstants.HEALTH_POINTS_EXCEPTION);
        }
        this.healthPoints = healthPoints;
    }

    public void addPoints(int points) {
        this.setPoints(this.getPoints() + points);
    }

    public int getMaxLevelPassed() {
        return this.maxLevelPassed;
    }

    public void setMaxLevelPassed(int levelPassed) {
        this.maxLevelPassed = levelPassed;
    }

    public PlayerCar getCar() {
        return this.car;
    }

    public void updateStatsAtEnd() {
        this.setHealthPoints(GameplayConstants.HEALTH_BAR_MAX);
        this.car.updateAmmunition(GameplayConstants.START_GAME_BULLETS_NORMAL_MODE);
        this.car.stopAccelerate();
        this.car.setCenterWheel(true);
        this.car.removeWind();
    }
}
