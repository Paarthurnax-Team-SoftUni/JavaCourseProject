package dataHandler;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Todor Popov using Lenovo on 20.4.2017 г. at 2:06.
 */
public class TrackParams extends Observable {
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
