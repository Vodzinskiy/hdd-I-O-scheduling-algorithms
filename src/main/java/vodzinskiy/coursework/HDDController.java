package vodzinskiy.coursework;

import vodzinskiy.coursework.algorithms.SchedulingAlgorithm;

public class HDDController {
    private final HDD hdd;
    private final SchedulingAlgorithm algorithm;


    public HDDController(HDD hdd, SchedulingAlgorithm algorithm) {
        this.hdd = hdd;
        this.algorithm = algorithm;
    }
}
