package interfaces;

public interface Movable {

    double getPositionX();

    double getPositionY();

    void setPosition(double x, double y);

    void setVelocity(double x, double y);

    void addVelocity(double x, double y,  int min, int max);


}
