package vodzinskiy.coursework.algorithms;

import vodzinskiy.coursework.Request;
import vodzinskiy.coursework.enums.ActiveQueue;

import java.util.ArrayList;
import java.util.List;

import static vodzinskiy.coursework.enums.ActiveQueue.FIRST;
import static vodzinskiy.coursework.enums.ActiveQueue.SECOND;

public class F_LOOK implements SchedulingAlgorithm {
    private final int maxRequestNumber;
    private final List<Request> firstQueue;
    private final List<Request> secondQueue;
    private ActiveQueue activeQueue;
    private boolean ascending;


    public F_LOOK(int maxRequestNumber) {
        this.maxRequestNumber = maxRequestNumber;
        this.firstQueue = new ArrayList<>();
        this.secondQueue = new ArrayList<>();
        this.activeQueue = FIRST;
        this.ascending = true;
    }

    @Override
    public void addRequest(Request request) throws IllegalStateException {
        if (activeQueue == FIRST) {
            if (secondQueue.size() == (maxRequestNumber / 2)) {
                throw new IllegalStateException("queue is full");
            }
            secondQueue.add(request);
        } else {
            if (firstQueue.size() == (maxRequestNumber / 2)) {
                throw new IllegalStateException("queue is full");
            }
            firstQueue.add(request);
        }
    }

    @Override
    public Request getRequest(int position) {
        if (activeQueue == FIRST) {
            if (firstQueue.isEmpty()) {
                activeQueue = SECOND;
                return look(position, secondQueue);
            }
            return look(position, firstQueue);
        } else {
            if (secondQueue.isEmpty()) {
                activeQueue = FIRST;
                return look(position, firstQueue);
            }
            return look(position, secondQueue);
        }
    }

    private Request look(int position, List<Request> queue) {
        if (queue.isEmpty()) {
            return null;
        }

        if (ascending) {
            Request requestWithBiggerTrackNumber = null;
            for (Request request : queue) {
                if (request.getTrackNumber() >= position) {
                    if (requestWithBiggerTrackNumber == null
                            || request.getTrackNumber() - position < requestWithBiggerTrackNumber.getTrackNumber() -position) {
                        requestWithBiggerTrackNumber = request;
                    }
                }
            }

            if (requestWithBiggerTrackNumber != null) {
                queue.remove(requestWithBiggerTrackNumber);
                return requestWithBiggerTrackNumber;
            } else {
                Request requestToBeExecuted = null;
                for (Request request : queue) {
                    if (requestToBeExecuted == null || request.getTrackNumber() > requestToBeExecuted.getTrackNumber()) {
                        requestToBeExecuted = request;
                    }
                }

                if (requestToBeExecuted != null) {
                    queue.remove(requestToBeExecuted);
                }

                ascending = false;
                return requestToBeExecuted;
            }
        } else {
            Request requestWithSmallerTrackNumber = null;
            for (Request request : queue) {
                if (request.getTrackNumber() <= position) {
                    if (requestWithSmallerTrackNumber == null
                            || position - request.getTrackNumber() < position - requestWithSmallerTrackNumber.getTrackNumber()) {
                        requestWithSmallerTrackNumber = request;
                    }
                }
            }

            if (requestWithSmallerTrackNumber != null) {
                queue.remove(requestWithSmallerTrackNumber);
                return requestWithSmallerTrackNumber;
            } else {
                Request requestToBeExecuted = null;
                for (Request request : queue) {
                    if (requestToBeExecuted == null || request.getTrackNumber() < requestToBeExecuted.getTrackNumber()) {
                        requestToBeExecuted = request;
                    }
                }

                if (requestToBeExecuted != null) {
                    queue.remove(requestToBeExecuted);
                }
                ascending = true;
                return requestToBeExecuted;
            }
        }
    }
}
