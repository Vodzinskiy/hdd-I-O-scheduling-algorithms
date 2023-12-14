package vodzinskiy.coursework;

import java.util.List;

public class Processor {
    public final int TIME_QUANTUM = 20;

    private final List<Process> processes;

    private final int requestsPerSecond;

    public Processor(List<Process> processes, int requestsPerSecond) {
        this.processes = processes;
        this.requestsPerSecond = requestsPerSecond;
    }
}
