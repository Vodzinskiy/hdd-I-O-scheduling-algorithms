package vodzinskiy.coursework;

import lombok.Getter;

public class Process {
    public static final int REQUEST_PROCESSING_TIME = 7;

    private final File file;

    @Getter
    private int requestsCounter = 0;

    public Process(File file) {
        this.file = file;
    }

    public void clearRequestsCounter() {
        requestsCounter = 0;
    }

    public void tick() {

    }
}
