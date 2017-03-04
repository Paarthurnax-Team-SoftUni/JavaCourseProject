package mapHandlers;

import mapHandlers.Levels.FirstLevel;

import java.io.IOException;

public class TrackHandler {

//    private static final Image backgroundLevel1 = ;

    public static Track getLevel(TrackLevel level) throws IOException {
        Track track = null;
        switch (level) {
            case FIRST_LEVEL: {
                track = new FirstLevel();
                break;
            }
        }

        return track;
    }
}
