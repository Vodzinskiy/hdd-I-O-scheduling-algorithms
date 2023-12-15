package vodzinskiy.coursework.algorithms;

import vodzinskiy.coursework.Request;

public class SSTF implements SchedulingAlgorithm{

    private final int maxRequestNumber;

    public SSTF(int maxRequestNumber) {
        this.maxRequestNumber = maxRequestNumber;
    }
    @Override
    public void addRequest(Request request) throws IllegalStateException {

    }

    @Override
    public Request getRequest(int position) {
        return null;
    }
}
