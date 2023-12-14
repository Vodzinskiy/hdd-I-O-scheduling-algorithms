package vodzinskiy.coursework;

public class HDD {

    public static int TRACKS_NUMBER = 500;
    public static int NUMBER_OF_SECTORS_PER_TRACK = 100;

    private static final int MOVING_TIME_PER_TRACK = 10;
    private static final int ROTATION_DELAY = 8;
    private static final int ALL_TRACKS_MOVEMENT_TIME = 130;

    private final boolean[][] tracks = new boolean[TRACKS_NUMBER][NUMBER_OF_SECTORS_PER_TRACK];

    public void markingSector(int block) {
        tracks[block / NUMBER_OF_SECTORS_PER_TRACK][block % NUMBER_OF_SECTORS_PER_TRACK] = true;
    }



}
