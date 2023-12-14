package vodzinskiy.coursework;

import vodzinskiy.coursework.algorithms.FCFS;
import vodzinskiy.coursework.algorithms.F_LOOK;
import vodzinskiy.coursework.algorithms.SSTF;
import vodzinskiy.coursework.algorithms.SchedulingAlgorithm;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int PROCESS_NUMBER = 10;
    public static int TRACK_NUMBER = 500;
    public static int NUMBER_OF_SECTORS_PER_TRACK = 100;
    public static int MAXIMUM_REQUEST_NUMBER = 20;
    public static int REQUESTS_NUMBER = 100000;

    Random random = new Random(0);

    public static void main(String[] args) {

        System.out.println("Choose an algorithm:");
        System.out.println("    1) FCFS");
        System.out.println("    2) SSTF");
        System.out.println("    3) F_LOOK");
        Scanner scanner = new Scanner(System.in);
        int algorithmNumber = scanner.nextInt();

        SchedulingAlgorithm algorithm = switch (algorithmNumber) {
            case 1 -> new FCFS();
            case 2 -> new SSTF();
            case 3 -> new F_LOOK();
            default -> throw new IllegalArgumentException("non-existent algorithm");
        };

        System.out.println("Number of requests per second:");
        int requestsPerSecond = scanner.nextInt();
    }
}