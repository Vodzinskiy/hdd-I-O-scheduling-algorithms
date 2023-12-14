package vodzinskiy.coursework;

import vodzinskiy.coursework.enums.RequestType;

public class Request {
    private final RequestType type;

    private final int sector;


    public Request(RequestType type, int sector) {
        this.type = type;
        this.sector = sector;
    }
}
