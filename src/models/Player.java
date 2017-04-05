package models;

import gameEngine.RunTrack;
import constants.CarConstants;
import models.sprites.PlayerCar;

public class Player {

    private String name;
    private Long highScore;
    private Double money;
    private Long points;
    private int healthPoints;
    private int maxLevelPassed;
    private boolean accelerating = false;
    private boolean centerWheel;
    private int id;
    private PlayerCar car;

    public Player() {
        this.points = 0L;
        this.car = new PlayerCar();
    }

    public Player(String name, Long highScore, Double money, int healthPoints) {
        this();
        this.name = name;
        this.highScore = highScore;
        this.money = money;
        this.healthPoints = healthPoints;
    }

    public void shot() {
        if (this.car.getAmmunition() > 0) {
            this.car.setAmmunition(this.car.getAmmunition() - 1);
            RunTrack.setShoot(true);
        }
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

    public void addPoints(int PointsToAdd) {
        this.setPoints(this.getPoints() + PointsToAdd);
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
        this.setHealthPoints(CarConstants.HEALTH_BAR_MAX);
        this.car.stopAccelerate();
        this.car.setCenterWheel(true);
        this.car.removeWind();
    }
}
