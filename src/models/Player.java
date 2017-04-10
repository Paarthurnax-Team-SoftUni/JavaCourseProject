package models;

import constants.GameplayConstants;
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
        this.points = 0L;
        this.car = playerCar;
    }

    public Player(PlayerCar playerCar, String name, Long highScore, Double money, int healthPoints) {
        this(playerCar);
        this.name = name;
        this.highScore = highScore;
        this.money = money;
        this.healthPoints = healthPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHighScore() {
        return highScore;
    }

    public void setHighScore(Long highScore) {
        this.highScore = highScore;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getPoints() {
        return this.points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
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
        this.car.stopAccelerate();
        this.car.setCenterWheel(true);
        this.car.removeWind();
    }
}
