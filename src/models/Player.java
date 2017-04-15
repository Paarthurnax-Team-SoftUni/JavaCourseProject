package models;

import utils.constants.ErrorsConstants;
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

    public int getId() {
        return id;
    }

    public void updateId(int id) {
        this.setId(id);
    }

    private void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException(ErrorsConstants.ID_EXCEPTION);
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
            throw new IllegalArgumentException(ErrorsConstants.NAME_EXCEPTION);
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
            throw new IllegalArgumentException(ErrorsConstants.HIGHSCORE_ERROR);
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
            throw new IllegalArgumentException(ErrorsConstants.MONEY_EXCEPTION);
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
            throw new IllegalArgumentException(ErrorsConstants.POINTS_EXCEPTION);
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
            throw new IllegalArgumentException(ErrorsConstants.HEALTH_POINTS_EXCEPTION);
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
        this.car.setAmmunition(GameplayConstants.START_GAME_BULLETS);
        this.car.stopAccelerate();
        this.car.setCenterWheel(true);
        this.car.removeWind();
    }
}
