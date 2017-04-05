package mapHandlers.levels;

import constants.CarConstants;

public enum TrackLevel {
    FIRST_LEVEL(CarConstants.FIRST_LEVEL_DRUNK_DRIVERS,
            CarConstants.TRACK_BACKGROUND_PATH,
            CarConstants.FIRST_LEVEL_MIN_X,
            CarConstants.FIRST_LEVEL_MAX_X),
    SECOND_LEVEL(CarConstants.SECOND_LEVEL_DRUNK_DRIVERS,
            CarConstants.SECOND_TRACK_BACKGROUND_PATH,
            CarConstants.SECOND_LEVEL_MIN_X,
            CarConstants.SECOND_LEVEL_MAX_X);

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