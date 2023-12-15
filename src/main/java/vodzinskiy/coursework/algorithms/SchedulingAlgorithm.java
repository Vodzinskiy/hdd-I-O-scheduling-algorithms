package vodzinskiy.coursework.algorithms;

import vodzinskiy.coursework.Request;

public interface SchedulingAlgorithm {
    void addRequest(Request request) throws IllegalStateException;

    Request getRequest(int position);
}
