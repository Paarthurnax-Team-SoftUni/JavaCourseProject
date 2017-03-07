package mapHandlers;

import dataHandler.Constants;

public enum TrackLevel {
    FIRST_LEVEL(100, Constants.TRACK_BACKGROUND_PATH, 120, 320), SECOND_LEVEL(60, Constants.SECOND_TRACK_BACKGROUND_PATH, 50, 400);
    private int value;
    private String path;
    private int minLeftSide;
    private int maxRightSide;

    TrackLevel(int value, String path, int minLeftSide, int maxRightSide) {
        this.value = value;
        this.path = path;
        this.minLeftSide = minLeftSide;
        this.maxRightSide = maxRightSide;
    }

    public int getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public int getMinLeftSide() {
        return minLeftSide;
    }

    public int getMaxRightSide() {
        return maxRightSide;
    }
}