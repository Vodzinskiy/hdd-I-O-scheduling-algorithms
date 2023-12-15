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

    private int[] requestsPerProcess;


    public Processor(List<Process> processes, int requestsPerSecond) {
        this.processes = processes;
        this.requestsPerSecond = requestsPerSecond;
        uniformDivision();
    }

    public void tick() {

        if (time % 1_000L == 0L) {
            for (Process p: processes) {
                p.clearRequestsCounter();
            }
        }

        Process process = processes.get(activeProcess);


        process.tick();
        processTime++;

        if (processTime == TIME_QUANTUM) {
            processChange();
        }
        time++;
    }

    public void uniformDivision() {
        requestsPerProcess = new int[PROCESS_NUMBER];
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
