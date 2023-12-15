package vodzinskiy.coursework.algorithms;

import vodzinskiy.coursework.Request;

public class FCFS implements SchedulingAlgorithm {

    private final int maxRequestNumber;

    public FCFS(int maxRequestNumber) {
        this.maxRequestNumber = maxRequestNumber;
    }
    @Override
    public void addRequest(Request request) {

    }

    @Override
    public Request getRequest(int position) {
        return null;
    }
}
