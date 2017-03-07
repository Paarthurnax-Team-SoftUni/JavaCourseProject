package mapHandlers;

import dataHandler.Constants;

public enum TrackLevel {
    FIRST_LEVEL(100, Constants.TRACK_BACKGROUND_PATH), SECOND_LEVEL(60, Constants.SECOND_TRACK_BACKGROUND_PATH);
    private int value;
    private String path;

    TrackLevel(int value, String path) {
        this.value = value;
        this.path = path;
    }

    public int getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }
}