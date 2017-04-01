package mapHandlers;

import constants.Constants;

public enum TrackLevel {
    FIRST_LEVEL(Constants.FIRST_LEVEL_DRUNK_DRIVERS, Constants.TRACK_BACKGROUND_PATH, Constants.FIRST_LEVEL_MIN_X, Constants.FIRST_LEVEL_MAX_X),
    SECOND_LEVEL(Constants.SECOND_LEVEL_DRUNK_DRIVERS, Constants.SECOND_TRACK_BACKGROUND_PATH, Constants.SECOND_LEVEL_MIN_X, Constants.SECOND_LEVEL_MAX_X);
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