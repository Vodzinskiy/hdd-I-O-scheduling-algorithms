package vodzinskiy.coursework;

import lombok.Getter;
import vodzinskiy.coursework.enums.RequestType;

import static vodzinskiy.coursework.HDD.SECTORS_PER_TRACK;

public class Request {
    @Getter
    private final RequestType type;

    private final int sector;
    @Getter
    private final Process process;


    public Request(RequestType type, int sector, Process process) {
        this.type = type;
        this.sector = sector;
        this.process = process;
    }

    public int getTrackNumber() {
        return sector / SECTORS_PER_TRACK;
    }
}
