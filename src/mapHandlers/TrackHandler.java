package mapHandlers;

import mapHandlers.Levels.FirstLevel;
import mapHandlers.Levels.SecondLevel;

import java.io.IOException;

public class TrackHandler {

//    private static final Image backgroundLevel1 = ;

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
