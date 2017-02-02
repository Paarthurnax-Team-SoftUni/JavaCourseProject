package DataHandler;


public class Player {


   private String name;
   private Long highScore;
   private Double money;
   private Long points;
   private Long experience;
   private int healthPoints;

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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", highScore=" + highScore +
                ", money=" + money +
                ", points=" + points +
                ", experience=" + experience +
                ", healthPoints=" + healthPoints +
                '}';
    }
}
