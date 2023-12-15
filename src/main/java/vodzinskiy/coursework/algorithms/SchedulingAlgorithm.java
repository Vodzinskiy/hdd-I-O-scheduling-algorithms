package vodzinskiy.coursework.algorithms;

import vodzinskiy.coursework.Request;

public interface SchedulingAlgorithm {
    void addRequest(Request request);

    Request getRequest(int position);
}
