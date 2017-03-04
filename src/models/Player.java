package models;

import GameEngine.RotatedImageInCanvas;
import GameEngine.RunTrack;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public class Player extends Sprite {

    private String name;
    private Long highScore;
    private Double money;
    private Long points;
    private Long experience;
    private int healthPoints;
    private double angle;
    private boolean accelerating = false;
    private boolean centerWheel;
    private boolean turnRight;
    private boolean turnLeft;


    public boolean getTurnRight() {
        return this.turnRight;
    }

    public void setTurnRight(boolean b) {
        this.turnRight = b;
    }

    public boolean getTurnLeft() {
        return this.turnLeft;
    }

    public void setTurnLeft(boolean b) {
        this.turnLeft = b;
    }

    public boolean getCenterWheel() {
        return this.centerWheel;
    }

    public void setCenterWheel(boolean b) {
        this.centerWheel = b;
    }

    public double getAngle() {
        return this.angle;
    }

    public void addAngle(double angle) {

    }

    public void setAngle(double angle) {
        System.out.println(this.angle + " - " + angle);
        if (angle < 45 && angle > -45) {
            this.angle = angle;
        }
//        else {
//            if (this.angle >= 45) this.angle--;
//            if (this.angle <= -45) this.angle++;
//        }
    }

    public Player(String name, Long highScore, Double money, Long points, Long experience, int healthPoints) {

        this.name = name;
        this.highScore = highScore;
        this.money = money;
        this.points = points;
        this.experience = experience;
        this.healthPoints = healthPoints;
        this.angle = 0;
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

    public void stopAccelerate() {
        this.accelerating = false;

    }

    @Override
    public void render(GraphicsContext gc) {
        RotatedImageInCanvas.drawRotatedImage(gc, this.image, angle, positionX, positionY);
        //super.render(gc);
    }

    @Override
    public void update() {
        if (turnLeft) {
            setAngle(getAngle() - 2);
        }
        if (turnRight) {
            setAngle(getAngle() + 2);
        }
        if (centerWheel) {
            if (this.angle < 0) {
                this.setAngle(this.getAngle() + 2);
            } else if (this.angle > 0)
                this.setAngle(this.getAngle() - 2);
            else centerWheel = false;
        }
        if (accelerating) {
            this.addVelocity(this.getAngle()/5, -2);
            if (RunTrack.getVelocity() < 20) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() + 0.1));
            }
        } else {
            this.addVelocity(this.getAngle()/5, 1);
            if (RunTrack.getVelocity() > 5) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() - 0.1));
            }
        }

        super.update();

    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.name, this.highScore);
    }


}
