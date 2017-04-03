package dataHandler;

import constants.ErrorsConstants;
import constants.FormattingConstants;
import constants.GeneralConstants;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import constants.CarConstants;

import java.util.Observable;

public class CurrentStats extends Observable {
    private SimpleLongProperty bullets;
    private SimpleStringProperty points;
    private SimpleLongProperty distance;
    private SimpleStringProperty time;

    public CurrentStats(long bullets, long points, long distance, long time) {
        this.bullets = new SimpleLongProperty();
        this.points = new SimpleStringProperty();
        this.distance = new SimpleLongProperty();
        this.time = new SimpleStringProperty();
        this.setBullets(bullets);
        this.setPoints(points);
        this.setDistance(distance);
        this.setTime(time);
    }

    public void updateBullets(long bullets) {
        this.setBullets(bullets);
    }

    public void updatePoints(long points) {
        this.setPoints(points);
    }

    public long getDistance(){
        return distance.get();
    }

    public LongProperty valueBullets(){
        return bullets;
    }

    public StringProperty valuePoints(){
        return points;
    }

    public LongProperty valueDistance(){
        return distance;
    }

    public StringProperty valueTime(){
        return time;
    }

    public void updateDistance(long distance) {
        this.setDistance(distance);
    }

    public void updateTime(long time) {
        this.setTime(time);
    }

    private void setBullets(long bullets) {
        if (bullets < 0) {
            throw new IllegalArgumentException(ErrorsConstants.BULLETS_EXCEPTION);
        }
        this.bullets.set(bullets);
    }

    private void setTime(long time){
        if (time < 0) {
            throw new IllegalArgumentException(ErrorsConstants.TIME_EXCEPTION);
        }
        time= (long)(CarConstants.TRACK_1_END_TIME* GeneralConstants.FRAMES_PER_SECOND)-time;
        int seconds=(int)(time % CarConstants.SECONDS_DURATION);
        this.time.set(String.format(FormattingConstants.SECONDS_FORMATTER,time/CarConstants.SECONDS_DURATION,seconds));
    }

    private void setPoints(long points){
        if (points < 0) {
            throw new IllegalArgumentException(ErrorsConstants.POINTS_EXCEPTION);
        }
        this.points.set(String.format(FormattingConstants.POINTS_FORMATTER, points));
    }

    private void setDistance(long distance) {
        if (distance < 0) {
            throw new IllegalArgumentException(ErrorsConstants.DISTANCE_EXCEPTION);
        }
        this.distance.set(distance);
    }
}
