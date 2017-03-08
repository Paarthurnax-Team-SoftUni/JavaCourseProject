package dataHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.Constants;

import java.util.Observable;

public class CurrentTime extends Observable {

    private SimpleStringProperty value = new SimpleStringProperty(this, Constants.VALUE_STRING);

    public CurrentTime(long input) {
        setValue(input);
    }

    public void setValue(long value){

        value= (long)(Constants.TRACK_1_END_TIME*0.017)-value;
        int seconds=(int)(value % 60);
        this.value.set(String.format("%02d:%02d",value/60,seconds));

    }

    public StringProperty valueProperty(){
        return value;
    }
}
