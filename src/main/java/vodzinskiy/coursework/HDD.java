package vodzinskiy.coursework;

public class HDD {

    private static final int MOVING_TIME_PER_TRACK = 10;
    private static final int ROTATION_DELAY = 8;
    private static final int ALL_TRACKS_MOVEMENT_TIME = 130;

    private final boolean[][] tracks;


    public HDD(boolean[][] tracks) {
        this.tracks = tracks;
    }
}
