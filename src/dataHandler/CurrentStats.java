package dataHandler;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import constants.CarConstants;

import java.util.Observable;

public class CurrentStats extends Observable {
    private SimpleLongProperty bullets = new SimpleLongProperty();
    private SimpleStringProperty points = new SimpleStringProperty();
    private SimpleLongProperty distance = new SimpleLongProperty();
    private SimpleStringProperty time = new SimpleStringProperty();

    public CurrentStats(long bullets, long points, long distance, long time) {
        this.setBullets(bullets);
        this.setPoints(points);
        this.setDistance(distance);
        this.setTime(time);
    }

    public void setBullets(long bullets){
        this.bullets.set(bullets);
    }

    public void setPoints(long points){
        this.points.set(String.format("%06d", points));
    }

    public long getDistance(){
        return distance.get();
    }

    public void setDistance(long distance){
        this.distance.set(distance);
    }

    public void setTime(long time){
        time= (long)(CarConstants.TRACK_1_END_TIME*0.017)-time;
        int seconds=(int)(time % 60);
        this.time.set(String.format("%02d:%02d",time/60,seconds));
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
}
