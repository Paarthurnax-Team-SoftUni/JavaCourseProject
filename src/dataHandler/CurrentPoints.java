package dataHandler;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.Constants;

import java.util.Observable;

public class CurrentPoints extends Observable{

    private SimpleStringProperty value = new SimpleStringProperty(this, Constants.VALUE_STRING);

    public CurrentPoints(long input) {
       setValue(input);
    }

    public void setValue(long value){this.value.set(String.format("%06d",value));}

    public StringProperty valueProperty(){
        return value;
    }

}
