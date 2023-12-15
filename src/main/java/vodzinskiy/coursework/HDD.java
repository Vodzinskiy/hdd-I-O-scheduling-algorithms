package vodzinskiy.coursework;

import lombok.Getter;
import vodzinskiy.coursework.enums.HDDState;

import static vodzinskiy.coursework.enums.HDDState.*;

public class HDD {

    public static int TRACKS_NUMBER = 500;
    public static int NUMBER_OF_SECTORS_PER_TRACK = 100;

    private static final int MOVING_TIME_PER_TRACK = 10;
    private static final int ROTATION_DELAY = 8;
    private static final int ALL_TRACKS_MOVEMENT_TIME = 130;

    @Getter
    private final boolean[][] tracks = new boolean[TRACKS_NUMBER][NUMBER_OF_SECTORS_PER_TRACK];

    @Getter
    private HDDState state;

    private int position = 0;
    private int movingPosition;

    private int movingTime = 0;

    private int waitingTime = 0;

    private boolean operationReady;


    public HDD() {
        state = INACTIVE;
    }

    public void markingSector(int block) {
        tracks[block / NUMBER_OF_SECTORS_PER_TRACK][block % NUMBER_OF_SECTORS_PER_TRACK] = true;
    }

    public void tick() {
        if (state == INACTIVE && !operationReady) {
            state = WAITING;
            waitingTime = 1;
        } else if (state == MOVING) {
            if (position == movingPosition) {
                state = WAITING;
                waitingTime = 1;
                return;
            }
            if (movingTime == MOVING_TIME_PER_TRACK) {
                if (position < movingPosition) {
                    position += 1;
                } else {
                    position -= 1;
                }
                movingTime = 1;
            } else {
                movingTime++;
            }

        } else if (state == WAITING) {
            if (waitingTime == ROTATION_DELAY) {
                state = INACTIVE;
                operationReady = true;
            } else {
                waitingTime += 1;
            }
        }
    }

    public void move(int trackNumber) {
        if (position == trackNumber) {
            state = WAITING;
        } else {
            state = MOVING;
            movingPosition = trackNumber;
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
