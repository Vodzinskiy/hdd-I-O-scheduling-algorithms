package vodzinskiy.coursework;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static vodzinskiy.coursework.Main.PROCESS_NUMBER;

public class Processor {
    public final int TIME_QUANTUM = 20;

    private final List<Process> processes;

    private final int requestsPerSecond;

    @Getter
    private int time = 0;
    private int processTime = 0;
    private int activeProcess = 0;

    private final int[] requestsPerProcess;


    public Processor(List<Process> processes, int requestsPerSecond) {
        this.processes = processes;
        this.requestsPerSecond = requestsPerSecond;
        requestsPerProcess = new int[PROCESS_NUMBER];
        uniformDivision();
    }

    public void execute() {
        if (time % 1000 == 0) {
            for (Process p : processes) {
                p.clearRequestsCounter();
            }
        }
        Process process = processes.get(activeProcess);
        if (process.isBlocked()) {
            Process nextProcess = null;
            for (int i = activeProcess + 1; i < processes.size(); i++) {
                if (!processes.get(i).isBlocked()) {
                    nextProcess = processes.get(i);
                    break;
                }
            }
            if (nextProcess != null) {
                process = nextProcess;
                activeProcess = processes.indexOf(process);
            } else {
                Process minProcess = null;
                int minIndex = Integer.MAX_VALUE;
                for (int i = 0; i < processes.size(); i++) {
                    Process p = processes.get(i);
                    if (!p.isBlocked()) {
                        int index = processes.indexOf(p);
                        if (index < minIndex) {
                            minProcess = p;
                            minIndex = index;
                        }
                    }
                }
                if (minProcess != null) {
                    process = minProcess;
                    activeProcess = processes.indexOf(minProcess);
                } else {
                    process = null;
                }
            }
        }

        if (process != null) {
            process.setCreation(process.getRequestsCounter() < requestsPerProcess[activeProcess]);
            process.execute();
            processTime++;
        }

        if (processTime == TIME_QUANTUM) {
            processChange();
        }
        time++;
    }

    public void uniformDivision() {
        Arrays.fill(requestsPerProcess, 0);
        for (int i = 0, j = 0; i < requestsPerSecond; i++, j = (j + 1) % PROCESS_NUMBER) {
            requestsPerProcess[j] += 1;
        }
    }

    public void processChange() {
        processTime = 0;
        activeProcess = (activeProcess + 1) % PROCESS_NUMBER;
    }
}
