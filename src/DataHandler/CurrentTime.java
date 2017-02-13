package DataHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Observable;

public class CurrentTime extends Observable {

    private SimpleStringProperty value = new SimpleStringProperty(this, "value");

    public CurrentTime(long input) {
        setValue(input);
    }

    public String getValue(){
        return value.get();
    }

    public void setValue(long value){
        int seconds= (int)(value % 60);
        this.value.set(String.format("%02d:%02d",value/60,seconds));

    }

    public StringProperty valueProperty(){
        return value;
    }
}
