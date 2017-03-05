package models;


import GameEngine.RunTrack;
import dataHandler.Constants;

public class Player extends Sprite {


    private String name;
    private Long highScore;
    private Double money;
    private Long points;
    private Long experience;
    private int healthPoints;
    private boolean accelerating = false;

    public Player(String name, Long highScore, Double money, Long points, Long experience, int healthPoints) {

        this.name = name;
        this.highScore = highScore;
        this.money = money;
        this.points = points;
        this.experience = experience;
        this.healthPoints = healthPoints;
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
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void givePoints(int playerPoints) {
        points += playerPoints;
    }

    public void accelerate() {
        this.accelerating = true;
    }

    public void updateHighScore(){
        if (this.getHighScore() < this.getPoints()) {
            this.setHighScore(this.getPoints());
        }
    }

    public void updateStatsAtEnd(){
        this.setHealthPoints(Constants.HEALTH_BAR_MAX);
        this.updateHighScore();
        this.setPoints(0L);
        this.stopAccelerate();
    }


    public void stopAccelerate() {
        this.accelerating = false;

    }

    @Override
    public void update() {
        if (accelerating) {
            this.addVelocity(0,-2);
            if (RunTrack.getVelocity() < 20) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity()+0.1));
            }
        } else {
            this.addVelocity(0,1);
            if (RunTrack.getVelocity() > 5) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity()-0.1));
            }
        }

        super.update();

    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.name, this.highScore);
    }
}
