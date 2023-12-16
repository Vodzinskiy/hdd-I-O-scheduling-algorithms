package vodzinskiy.coursework.algorithms;

import vodzinskiy.coursework.Request;

import java.util.ArrayList;
import java.util.List;

public class FCFS implements SchedulingAlgorithm {

    private final int maxRequestNumber;

    private final List<Request> queue;


    public FCFS(int maxRequestNumber) {
        this.maxRequestNumber = maxRequestNumber;
        this.queue = new ArrayList<>();
    }
    @Override
    public void addRequest(Request request) throws IllegalStateException {
        if (queue.size() == maxRequestNumber) {
            throw new IllegalStateException("queue is full");
        }
        queue.add(request);
    }

    @Override
    public Request getRequest(int position) {
        if (!queue.isEmpty()) {
            return queue.removeFirst();
        } else {
            return null;
        }
    }
}
