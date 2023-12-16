package vodzinskiy.coursework.algorithms;

import vodzinskiy.coursework.Request;

import java.util.ArrayList;
import java.util.List;

public class SSTF implements SchedulingAlgorithm{

    private final int maxRequestNumber;
    private final List<Request> queue;

    public SSTF(int maxRequestNumber) {
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
        Request closestTrackRequest = null;
        int closestTrackDistance = Integer.MAX_VALUE;
        for (Request request : queue) {
            int trackDistance = Math.abs(position - request.getTrackNumber());
            if (trackDistance < closestTrackDistance) {
                closestTrackRequest = request;
                closestTrackDistance = trackDistance;
            }
        }
        if (closestTrackRequest != null) {
            queue.remove(closestTrackRequest);
        }
        return closestTrackRequest;
    }
}
