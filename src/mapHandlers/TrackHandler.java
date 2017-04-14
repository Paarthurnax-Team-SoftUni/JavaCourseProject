package mapHandlers;

import interfaces.Track;

import java.io.IOException;

public class TrackHandler {

    public Track getLevel(int level) throws IOException {
        TrackLevel trackLevel = TrackLevel.values()[level - 1];
        return new TrackImpl(trackLevel);
    }
}
