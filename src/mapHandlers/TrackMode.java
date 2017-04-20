package mapHandlers;


import static utils.constants.GameplayConstants.*;

/*
 *  Holds information about the parameters of different modes
 */


public enum TrackMode {
    SAND_BOX(TRACK_SANDBOX_END_TIME, TRACK_POINTS_PER_DISTANCE, TRACK_SAMDBOX_END_DISTANCE, CHEAT_BULLETS_COUNT),
    DRAG(TRACK_DRAG_END_TIME, DRAG_MODE_POINTS_PER_DISTANCE, TRACK_DRAG_END_DISTANCE, START_GAME_BULLETS_NORMAL_MODE),
    POINT_HUNGER(TRACK_POINT_HUNGER_END_TIME, TRACK_POINTS_PER_DISTANCE, TRACK_POINT_HUNGER_END_DISTANCE, START_GAME_BULLETS_NORMAL_MODE);

    private long endTruckTime;
    private int pointsPerDistance;
    private long finalExpectedDistance;
    private int initialAmmunition;

    TrackMode(long endTruckTime, int pointsPerDistance, long finalExpectedDistance, int initialAmmunition) {
        this.endTruckTime = endTruckTime;
        this.pointsPerDistance = pointsPerDistance;
        this.finalExpectedDistance = finalExpectedDistance;
        this.initialAmmunition = initialAmmunition;
    }

    public long getEndTruckTime() {
        return this.endTruckTime;
    }

    public int getPointsPerDistance() {
        return this.pointsPerDistance;
    }

    public long getFinalExpectedDistance() {
        return this.finalExpectedDistance;
    }

    public int getInitialAmmunition() {
        return this.initialAmmunition;
    }
}
