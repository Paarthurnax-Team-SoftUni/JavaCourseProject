package models;

import constants.CarConstants;
import constants.ErrorsConstants;
import constants.GameplayConstants;
import gameEngine.RotatedImageInCanvas;
import gameEngine.RunTrack;
import interfaces.Playable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PlayerImpl implements Playable {

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
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean turnRight;
    private boolean turnLeft;
    private double angle;
    private int minLeftSide;
    private int maxRightSide;

    public PlayerImpl(String name, Long highScore, Double money, int healthPoints) {
        this();
        this.name = name;
        this.highScore = highScore;
        this.money = money;
        this.healthPoints = healthPoints;
        this.accelerating = false;
    }

    public PlayerImpl() {
        this.points = 0L;
        this.ammunition = CarConstants.START_GAME_BULLETS;
    }

    @Override
    public void shot() {
        if (getAmmunition() > 0) {
            setAmmunition(getAmmunition() - 1);
            RunTrack.setShoot(true);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void updateId(int id) {
        setId(id);
    }

    @Override
    public void updateName(String name) {
        this.setName(name);
    }

    @Override
    public Long getHighScore() {
        return highScore;
    }

    @Override
    public Double getMoney() {
        return money;
    }

    @Override
    public Integer getAmmunition() {
        return ammunition;
    }

    @Override
    public void updateAmmunition(int ammunition) {
        this.setAmmunition(ammunition);
    }

    @Override
    public void addVelocity(double x, double y, int min, int max) {
        this.minLeftSide = min;
        this.maxRightSide = max;
        if (x < 0) {
            if (this.positionX > min) {
                this.velocityX += x;
            }
        } else if (x > 0) {
            if (this.positionX < max) {
                this.velocityX += x;
            }
        }
        if (y < 0) {
            if (this.positionY > 300) {
                this.velocityY += y;
            }
        } else if (y > 0) {
            if (this.positionY < CarConstants.CANVAS_HEIGHT - this.height * 2) {
                this.velocityY += y;
            }
        }
    }

    @Override
    public Long getPoints() {
        return this.points;
    }

    @Override
    public int getHealthPoints() {
        return this.healthPoints;
    }

    @Override
    public void addPoints(long pointsToAdd) {
        this.setPoints(this.getPoints() + pointsToAdd);
    }

    @Override
    public int getMaxLevelPassed() {
        return this.maxLevelPassed;
    }

    @Override
    public void updateMaxLevel(int level) {
        this.setMaxLevelPassed(level);
    }

    @Override
    public void setCenterWheel(boolean b) {
        this.centerWheel = b;
    }

    @Override
    public void accelerate() {
        this.accelerating = true;
    }

    @Override
    public void updateStatsAtEnd() {
        this.setHealthPoints(CarConstants.HEALTH_BAR_MAX);
        this.stopAccelerate();
        this.setCenterWheel(true);
        this.removeWind();
    }

    @Override
    public void stopAccelerate() {
        this.accelerating = false;
    }

    @Override
    public void updatePoints(long points) {
        this.setPoints(points);
    }

    @Override
    public void updateHighScore(long score) {
        this.setHighScore(score);
    }

    @Override
    public void updateMoney(double money) {
        this.setMoney(money);
    }

    @Override
    public void updateHealthPoints(int healthPoints) {
        this.setHealthPoints(healthPoints);
    }

    @Override
    public void update() {
        if (this.centerWheel) {
            if (this.getAngle() < 0) {
                this.updateAngle(this.getAngle() + 5);
            } else if (this.getAngle() > 0)
                this.updateAngle(this.getAngle() - 5);
            else this.centerWheel = false;
        }
        if (this.accelerating) {
            this.addVelocity(0, -2, this.minLeftSide, this.maxRightSide);
            if (RunTrack.getVelocity() < 20) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() + 0.1));
            }
        } else {
            this.addVelocity(0, 1, this.minLeftSide, this.maxRightSide);
            if (RunTrack.getVelocity() > 5) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() - 0.1));
            }
        }

        this.update(this.minLeftSide, this.maxRightSide);
    }

    @Override
    public void update(int min, int max) {
        if (this.turnLeft) {
            updateAngle(getAngle() - 4);
        }
        if (this.turnRight) {
            updateAngle(getAngle() + 4);
        }
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / 3, 0, min, max);
        positionX += velocityX;
        positionY += velocityY;
    }

    @Override
    public void render(GraphicsContext gc) {
        RotatedImageInCanvas.drawRotatedImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    @Override
    public void updateImage(String filename) {
        Image image = new Image(filename);
        this.setImage(image);
    }

    @Override
    public Image getImage() {
        return this.image;
    }


    @Override
    public double getPositionX() {
        return this.positionX;
    }

    @Override
    public double getPositionY() {
        return this.positionY;
    }

    @Override
    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public void setVelocity(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    @Override
    public double getAngle() {
        return this.angle;
    }

    @Override
    public void updateAngle(double angle) {
        this.setAngle(angle);
    }


    @Override
    public void setTurnRight(boolean isTurning) {
        this.turnRight = isTurning;
    }

    @Override
    public void setTurnLeft(boolean isTurning) {
        this.turnLeft = isTurning;
    }


    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX, this.positionY, this.width, this.height);
    }

    private void setImage(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    private void setAngle(double angle) {
        if (angle < GameplayConstants.TURNING_ANGLE && angle > - GameplayConstants.TURNING_ANGLE) {
            this.angle = angle;
        }
    }

    private void setName(String name) {
        if(!name.equals("")) {
            this.name = name;

        }
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

    private void removeWind(){
        this.velocityX = GameplayConstants.INITIAL_VELOCITY;
        this.velocityY = GameplayConstants.INITIAL_VELOCITY;
        this.setAngle(GameplayConstants.INITIAL_VELOCITY);
        this.setTurnRight(false);
        this.setTurnLeft(false);
    }

    private void setAmmunition(Integer ammunition) {
        if (ammunition < 0 || ammunition > GameplayConstants.CHEAT_BULLETS_COUNT) {
            throw new IllegalArgumentException(ErrorsConstants.BULLETS_EXCEPTION);
        }
        this.ammunition = ammunition;
    }

    private void setMaxLevelPassed(int levelPassed) {
        if (levelPassed < 0) {
            throw new IllegalArgumentException(ErrorsConstants.LEVEL_EXCEPTION);
        }
        this.maxLevelPassed = levelPassed;
    }
}
