package models;

public interface Player {
    int getId();
    void updateId(int id);
    void updateName(String name);
    Long getHighScore();
    void updateHighScore(long score);
    Double getMoney();
    void updateMoney(double money);
    Integer getAmmunition();
    void updateAmmunition(int ammunition);
    Long getPoints();
    void addPoints(long pointsToAdd);
    int getHealthPoints();
    void updateHealthPoints(int healthPoints);
    int getMaxLevelPassed();
    void updateMaxLevel(int level);

}
