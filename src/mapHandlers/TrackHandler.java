package mapHandlers;

import mapHandlers.levels.FirstLevel;
import mapHandlers.levels.SecondLevel;
import mapHandlers.levels.TrackLevel;

import java.io.IOException;

public class TrackHandler {

    public Track getLevel(TrackLevel level) throws IOException {
        Track track = null;
        switch (level) {
            case FIRST_LEVEL:
                track = new FirstLevel();
                break;
            case SECOND_LEVEL:
                track = new SecondLevel();
                break;

        }

        return track;
    }
}
