package DataHandler;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Observable;

public class CurrentPoints extends Observable{

    private SimpleStringProperty value = new SimpleStringProperty(this, "value");

    public CurrentPoints(long input) {
       setValue(input);
    }

    public String getValue(){
        return value.get();
    }

    public void setValue(long value){this.value.set(String.format("%06d",value));}

    public StringProperty valueProperty(){
        return value;
    }

}
