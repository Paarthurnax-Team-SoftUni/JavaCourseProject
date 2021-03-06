package dataHandler;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.constants.CarConstants;
import utils.constants.ErrorConstants;
import utils.constants.GeneralConstants;
import utils.constants.StylesConstants;

import java.util.Observable;

public class CurrentStats extends Observable {
    private SimpleLongProperty bullets;
    private SimpleStringProperty points;
    private SimpleLongProperty distance;
    private SimpleStringProperty time;
    private long endTruckTime;

    public CurrentStats(long bullets, long points, long distance, long time, long endTruckTime) {
        this.bullets = new SimpleLongProperty();
        this.points = new SimpleStringProperty();
        this.distance = new SimpleLongProperty();
        this.time = new SimpleStringProperty();
        this.setEndTruckTime(endTruckTime);
        this.setBullets(bullets);
        this.setPoints(points);
        this.setDistance(distance);
        this.setTime(time);
    }

    public long getDistance() {
        return distance.get();
    }

    private void setDistance(long distance) {
        if (distance < 0) {
            throw new IllegalArgumentException(ErrorConstants.DISTANCE_EXCEPTION);
        }
        this.distance.set(distance);
    }

    public LongProperty valueBullets() {
        return this.bullets;
    }

    public StringProperty valuePoints() {
        return this.points;
    }

    public LongProperty valueDistance() {
        return this.distance;
    }

    public StringProperty valueTime() {
        return this.time;
    }

    public void updateDistance(long distance) {
        this.setDistance(distance);
    }

    public void updateTime(long time) {
        this.setTime(time);
    }

    public void updateBullets(long bullets) {
        this.setBullets(bullets);
    }

    public void updatePoints(long points) {
        this.setPoints(points);
    }

    public void updateEndTruckTime(long endTruckTime) {
        this.setEndTruckTime(endTruckTime);
    }

    private void setBullets(long bullets) {
        if (bullets < 0) {
            throw new IllegalArgumentException(ErrorConstants.BULLETS_EXCEPTION);
        }
        this.bullets.set(bullets);
    }

    private void setTime(long time) {
        time = (long) (this.endTruckTime * GeneralConstants.FRAMES_PER_SECOND) - time;
        int seconds = (int) (time % CarConstants.SECONDS_DURATION);
        this.time.set(String.format(StylesConstants.SECONDS_FORMATTER, time / CarConstants.SECONDS_DURATION, seconds));
    }

    private void setPoints(long points) {
        if (points < 0) {
            throw new IllegalArgumentException(ErrorConstants.POINTS_EXCEPTION);
        }
        this.points.set(String.format(StylesConstants.POINTS_FORMATTER, points));
    }

    private void setEndTruckTime(long endTruckTime) {
        this.endTruckTime = endTruckTime;
    }
}
