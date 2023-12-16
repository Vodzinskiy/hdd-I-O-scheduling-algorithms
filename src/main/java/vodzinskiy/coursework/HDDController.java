package vodzinskiy.coursework;

import lombok.Getter;
import vodzinskiy.coursework.algorithms.SchedulingAlgorithm;
import vodzinskiy.coursework.enums.ControllerState;
import vodzinskiy.coursework.enums.HDDState;

import java.util.ArrayList;
import java.util.List;

import static vodzinskiy.coursework.enums.ControllerState.EXECUTION;
import static vodzinskiy.coursework.enums.ControllerState.INACTIVE;

public class HDDController {
    private final HDD hdd;
    private final SchedulingAlgorithm algorithm;
    @Getter
    private final List<Integer> requestTimes;
    private int requestTime = 0;
    private ControllerState state;
    private Request request;

    public HDDController(HDD hdd, SchedulingAlgorithm algorithm) {
        this.hdd = hdd;
        this.algorithm = algorithm;
        requestTimes = new ArrayList<>();
        state = INACTIVE;
    }

    public void addRequest(Request request) {
        algorithm.addRequest(request);
    }

    public void executeRequest() {
        Request r = algorithm.getRequest(hdd.getPosition());
        if (r != null) {
            hdd.move(r.getTrackNumber());
            requestTime = 0;
            request = r;
            state = EXECUTION;
        } else {
            state = INACTIVE;
        }
    }

    public void tick() {
        if (state == INACTIVE) {
            executeRequest();
        } else {
            requestTime++;
            if (hdd.getState() == HDDState.INACTIVE &&
                    hdd.isOperationReady()) {
                hdd.operationExecution();
                requestTimes.add(requestTime);
                request.getProcess().processing(request);
                executeRequest();
            }
        }
    }
}