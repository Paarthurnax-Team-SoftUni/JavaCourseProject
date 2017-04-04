package interfaces;

public interface Savable {
    int getId();

    void updateId(int id);

    String getName();

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
