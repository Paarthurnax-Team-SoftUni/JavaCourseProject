package DataHandler;


import KeyHandler.KeyHandlerOnPress;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Player extends Sprite{

   private String name;
   private Long highScore;
   private Double money;
   private Long points;
   private Long experience;
   private int healthPoints;
   public EventHandler<KeyEvent> control;

    public Player(String name, Long highScore, Double money, Long points, Long experience, int healthPoints) {
        this.name = name;
        this.highScore = highScore;
        this.money = money;
        this.points = points;
        this.experience = experience;
        this.healthPoints = healthPoints;
        this.isAccelerate=false;
        this.isGoLeft=false;
        this.isGoRight=false;
        this.isStoping=false;
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
     public void givePoints(int playerPoints){
         points += playerPoints;
     }

    @Override
    public String toString() {
        return String .format("%s: %s", this.name, this.highScore);
    }

    public void goLeft(){

    }
    public void addControll(){
        this.control=new KeyHandlerOnPress(this);
    }
    public boolean isAccelerate;
    public boolean isGoLeft;
    public boolean isGoRight;
    public boolean isStoping;
    public void goRight(){}
    public void spiranje(){}
    public void accelerate(){}
}

