package DataHandler;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.Observable;

public class CurrentPoints extends Observable{

    private SimpleLongProperty value = new SimpleLongProperty(this, "value");

    public CurrentPoints(long input) {
       setValue(input);
    }

    public long getValue(){
        return value.get();
    }

    public void setValue(long value){
        this.value.set(value);
    }

    public LongProperty valueProperty(){
        return value;
    }

}
