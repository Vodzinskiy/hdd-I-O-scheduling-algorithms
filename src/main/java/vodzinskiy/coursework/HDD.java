package vodzinskiy.coursework;

import vodzinskiy.coursework.enums.HDDState;

import static vodzinskiy.coursework.enums.HDDState.*;

public class HDD {

    public static int TRACKS_NUMBER = 500;
    public static int NUMBER_OF_SECTORS_PER_TRACK = 100;

    private static final int MOVING_TIME_PER_TRACK = 10;
    private static final int ROTATION_DELAY = 8;
    private static final int ALL_TRACKS_MOVEMENT_TIME = 130;

    private final boolean[][] tracks = new boolean[TRACKS_NUMBER][NUMBER_OF_SECTORS_PER_TRACK];

    private HDDState state;

    private int position = 0;
    private int movingTrackNumber;

    private boolean operationReady;


    public HDD() {
        state = INACTIVE;
    }

    public void markingSector(int block) {
        tracks[block / NUMBER_OF_SECTORS_PER_TRACK][block % NUMBER_OF_SECTORS_PER_TRACK] = true;
    }

    public void move(int trackNumber) {
        if (position == trackNumber) {
            state = WAITING;
        } else {
            state = MOVING;
            movingTrackNumber = trackNumber;
        }
    }

    public void operationExecution() {
        if (state == INACTIVE && operationReady) {
            operationReady = false;
        } else {
            throw new IllegalStateException("HDD is busy");
        }
    }


}
