package models;

import constants.ErrorsConstants;
import constants.GameplayConstants;
import gameEngine.RunTrack;
import constants.CarConstants;

public class PlayerImlp extends SpriteImpl {

    private String name;
    private Long highScore;
    private Double money;
    private Long points;
    private Integer ammunition;
    private int healthPoints;
    private int maxLevelPassed;
    private boolean accelerating;
    private boolean centerWheel;
    private int id;

    public PlayerImlp(String name, Long highScore, Double money, int healthPoints) {
        this();
        this.name = name;
        this.highScore = highScore;
        this.money = money;
        this.healthPoints = healthPoints;
        this.accelerating = false;
    }

    public PlayerImlp() {
        this.points = 0L;
        this.ammunition = CarConstants.START_GAME_BULLETS;
    }

    public void shot() {
        if (getAmmunition() > 0) {
            setAmmunition(getAmmunition() - 1);
            RunTrack.setShoot(true);
        }
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void updateId(int id) {
        setId(id);
    }

    public void updateName(String name) {
        this.setName(name);
    }

    public Long getHighScore() {
        return highScore;
    }

    public Double getMoney() {
        return money;
    }

    public Integer getAmmunition() {
        return ammunition;
    }

    public void updateAmmunition(int ammunition) {
        this.setAmmunition(ammunition);
    }

    private void setAmmunition(Integer ammunition) {
        if (ammunition < 0 || ammunition > GameplayConstants.CHEAT_BULLETS_COUNT) {
            throw new IllegalArgumentException(ErrorsConstants.BULLETS_EXCEPTION);
        }
        this.ammunition = ammunition;
    }

    public Long getPoints() {
        return this.points;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public void addPoints(long pointsToAdd) {
        this.setPoints(this.getPoints() + pointsToAdd);
    }

    public int getMaxLevelPassed() {
        return this.maxLevelPassed;
    }

    public void updateMaxLevel(int level) {
        this.setMaxLevelPassed(level);
    }

    public void setCenterWheel(boolean b) {
        this.centerWheel = b;
    }

    public void accelerate() {
        this.accelerating = true;
    }

    public void updateStatsAtEnd() {
        this.setHealthPoints(CarConstants.HEALTH_BAR_MAX);
        this.stopAccelerate();
        this.setCenterWheel(true);
        this.removeWind();
    }

    public void stopAccelerate() {
        this.accelerating = false;
    }

    @Override
    public void update() {
        if (this.centerWheel) {
            if (this.getAngle() < 0) {
                this.setAngle(this.getAngle() + 5);
            } else if (this.getAngle() > 0)
                this.setAngle(this.getAngle() - 5);
            else this.centerWheel = false;
        }
        if (accelerating) {
            this.addVelocity(0, -2);
            if (RunTrack.getVelocity() < 20) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() + 0.1));
            }
        } else {
            this.addVelocity(0, 1);
            if (RunTrack.getVelocity() > 5) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() - 0.1));
            }
        }

        super.update();
    }

    public void updateHighScore(long score) {
        this.setHighScore(score);
    }

    public void updateMoney(double money) {
        this.setMoney(money);
    }

    public void updateHealthPoints(int healthPoints) {
        this.setHealthPoints(healthPoints);
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s: %s %d %s", this.name, this.highScore, this.id, this.healthPoints);
    }

    private void setHealthPoints(int healthPoints) {
        if(healthPoints < 0) {
            throw new IllegalArgumentException(ErrorsConstants.HEALTH_POINTS_EXCEPTION);
        }
        this.healthPoints = healthPoints;
    }

    private void setMoney(Double money) {
        if(money < 0.0) {
            throw new IllegalArgumentException(ErrorsConstants.MONEY_EXCEPTION);
        }
        this.money = money;
    }

    private void setHighScore(Long highScore) {
        if(highScore < 0) {
            throw new IllegalArgumentException(ErrorsConstants.HIGHSCORE_ERROR);
}
        this.highScore = highScore;
}

    private void setPoints(Long points) {
        if (points < 0) {
            throw new IllegalArgumentException(ErrorsConstants.POINTS_EXCEPTION);
}
        this.points = points;
}

    private void setId(int id) {
        if(id < 0) {
            throw new IllegalArgumentException(ErrorsConstants.ID_EXCEPTION);
}
        this.id = id;
    }

    private void setMaxLevelPassed(int levelPassed) {
        if (levelPassed < 0) {
            throw new IllegalArgumentException(ErrorsConstants.LEVEL_EXCEPTION);
        }
        this.maxLevelPassed = levelPassed;
    }
}
